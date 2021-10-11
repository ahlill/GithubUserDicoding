package com.example.githubuser.ui.intro

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.githubuser.databinding.ActivitySplashScreenBinding
import com.example.githubuser.ui.main.MainActivity
import com.example.githubuser.ui.setting.SettingPreferences
import com.example.githubuser.ui.setting.ViewModelSetting
import com.example.githubuser.ui.setting.ViewModelSettingFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")

class SplashScreenActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySplashScreenBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        val pref = SettingPreferences.getInstance(dataStore)
        val viewModelSetting = ViewModelProvider(this, ViewModelSettingFactory(pref)).get(ViewModelSetting::class.java)

        viewModelSetting.getThemeSettings().observe(this, { isModeDay ->
            if (isModeDay) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        })

        lifecycleScope.launch {
            delay(3000)
            val i = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}