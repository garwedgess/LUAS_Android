package eu.wedgess.luas.features.forecast

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerAppCompatActivity
import eu.wedgess.luas.R
import eu.wedgess.luas.domain.model.StopInformationEntity
import eu.wedgess.luas.presentation.model.Resource
import eu.wedgess.luas.presentation.model.Status
import eu.wedgess.luas.presentation.model.Stops
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

        // remove initial layout animations for config changes etc.
        if (savedInstanceState != null) {
            forecastActivityRV.layoutAnimation = null
        }

        viewModel = ViewModelProviders.of(this@TramForecastActivity, factory)
            .get(TramForecastViewModel::class.java)

        forecastActivityRetryBTN.setOnClickListener {
            viewModel.getStopForecast()
        }
        forecastActivitySwipeRefresh.setColorSchemeColors(
            ContextCompat.getColor(this@TramForecastActivity, R.color.colorAccent),
            ContextCompat.getColor(this@TramForecastActivity, R.color.colorPrimaryLight)
        )
        observeStopForecastData()
        observeStopNameData()
    }

    override fun onResume() {
        super.onResume()
        if (::viewModel.isInitialized) {
            viewModel.getStopForecast()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.forecast_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.refresh_forecast) {
            setSwipeRefreshLoadingState(isLoading = true)
            viewModel.getStopForecast()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun observeStopForecastData() {
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
                    supportActionBar?.subtitle = stopInfo.time
                    setStopStatusMessage(stopInfo.message)
                    val trams = when (viewModel.getStopNameResult().value) {
                        Stops.STILLORGAN -> {
                            stopInfo.inboundTrams
                        }
                        else -> {
                            // Stops.MARLBOROUGH
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

    private fun setStopStatusMessage(message: String) {
        val operationStatusColorResId =
            if (message.contains(getString(R.string.normal_operation_status))) {
                R.color.operation_status_normal
            } else {
                R.color.operation_status_other
            }
        forecastActivityStopMessageTV.text = message
        forecastActivityStopMessageTV.setBackgroundColor(
            ContextCompat.getColor(
                this@TramForecastActivity,
                operationStatusColorResId
            )
        )
    }

    private fun toggleRecyclerViewVisibility(visible: Boolean) {
        if (visible) {
            forecastActivityStopMessageTV.visibility = View.VISIBLE
            forecastActivityRV.visibility = View.VISIBLE
            forecastActivityNoDataTV.visibility = View.GONE
            forecastActivityRetryBTN.visibility = View.GONE
        } else {
            forecastActivityStopMessageTV.visibility = View.GONE
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
                    Stops.STILLORGAN -> {
                        getString(R.string.station_name_stillorgan)
                    }
                    Stops.MARLBOROUGH -> {
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