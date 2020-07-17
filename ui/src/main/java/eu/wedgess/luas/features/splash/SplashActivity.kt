package eu.wedgess.luas.features.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import eu.wedgess.luas.R
import eu.wedgess.luas.features.forecast.TramForecastActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // disable night mode as no time to theme
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        TramForecastActivity.startActivity(this@SplashActivity)
        finish()
    }
}