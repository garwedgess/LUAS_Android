package eu.wedgess.luas.features.forecast

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerAppCompatActivity
import eu.wedgess.luas.R
import eu.wedgess.luas.domain.model.StopInformationEntity
import eu.wedgess.luas.presentation.model.Resource
import eu.wedgess.luas.presentation.model.StationsEnum
import eu.wedgess.luas.presentation.model.Status
import eu.wedgess.luas.presentation.viewmodels.TramForecastViewModel
import kotlinx.android.synthetic.main.activity_tram_forecast.*
import timber.log.Timber
import javax.inject.Inject

class TramForecastActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: TramForecastViewModel
    private lateinit var adapter: TramForecastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tram_forecast)

        adapter = TramForecastAdapter()
        forecastActivityRV.adapter = adapter

        viewModel = ViewModelProviders.of(this@TramForecastActivity, factory)
            .get(TramForecastViewModel::class.java)

        forecastActivityRetryBTN.setOnClickListener {
            viewModel.getTramsForecast()
        }
        observeForecastData()
        observeStopNameData()
        viewModel.getTramsForecast()
    }

    override fun onResume() {
        super.onResume()
        if (::viewModel.isInitialized) {
            viewModel.getTramsForecast()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.forecast_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.refresh_forecast) {
            setSwipeRefreshLoadingState(isLoading = true)
            viewModel.getTramsForecast()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun observeForecastData() {
        if (!viewModel.getStopForecastResult().hasActiveObservers()) {
            viewModel.getStopForecastResult()
                .observe(this@TramForecastActivity, Observer { stopInfo ->
                    Timber.i("Received forecast: $stopInfo")
                    handleStopInfoResult(stopInfo)
                })
        }
    }

    private fun handleStopInfoResult(stopInfoResult: Resource<StopInformationEntity>) {
        when (stopInfoResult.status) {
            Status.SUCCESS -> {
                setSwipeRefreshLoadingState(isLoading = false)
                stopInfoResult.data?.let { stopInfo ->
                    supportActionBar?.subtitle = stopInfo.requestTime
                    val trams = when (viewModel.getStopNameResult().value) {
                        StationsEnum.STILLORGAN -> {
                            stopInfo.inboundTrams
                        }
                        else -> {
                            // StationsEnum.MARLBOROUGH
                            stopInfo.outboundTrams
                        }
                    }
                    adapter.submitList(trams)
                    toggleRecyclerViewVisibility(visible = true)
                } ?: Timber.e("Something went wrong stopInfoResult is SUCCESS but data is null")
            }
            Status.ERROR -> {
                setSwipeRefreshLoadingState(isLoading = false)
                forecastActivityNoDataTV.text = stopInfoResult.message
                toggleRecyclerViewVisibility(visible = false)
            }
            Status.LOADING -> {
                setSwipeRefreshLoadingState(isLoading = true)
            }
        }
    }

    private fun toggleRecyclerViewVisibility(visible: Boolean) {
        if (visible) {
            forecastActivityRV.visibility = View.VISIBLE
            forecastActivityNoDataTV.visibility = View.GONE
            forecastActivityRetryBTN.visibility = View.GONE
        } else {
            forecastActivityRV.visibility = View.GONE
            forecastActivityNoDataTV.visibility = View.VISIBLE
            forecastActivityRetryBTN.visibility = View.VISIBLE
        }
    }

    private fun observeStopNameData() {
        if (!viewModel.getStopNameResult().hasActiveObservers()) {
            viewModel.getStopNameResult().observe(this@TramForecastActivity, Observer { stopName ->
                Timber.i("Received stop name: ${stopName.name}")
                val name = when (stopName) {
                    StationsEnum.STILLORGAN -> {
                        getString(R.string.station_name_stillorgan)
                    }
                    StationsEnum.MARLBOROUGH -> {
                        getString(R.string.station_name_marlborough)
                    }
                }
                supportActionBar?.title = name
            })
        }
    }

    private fun setSwipeRefreshLoadingState(isLoading: Boolean) {
        forecastActivitySwipeRefresh.apply {
            isEnabled = false
            isRefreshing = isLoading
        }
    }


    companion object {

        @JvmStatic
        fun startActivity(callingActivity: AppCompatActivity) =
            callingActivity.startActivity(
                Intent(
                    callingActivity,
                    TramForecastActivity::class.java
                )
            )
    }
}