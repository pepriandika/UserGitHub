package com.firstproject.usergithub.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.firstproject.usergithub.R
import com.firstproject.usergithub.utils.SettingPreferences
import com.firstproject.usergithub.utils.dataStore
import com.firstproject.usergithub.viewmodel.SettingViewModel
import com.firstproject.usergithub.viewmodel.ViewModelFactory
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportActionBar?.title = "Setting"

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(application.dataStore)

        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]

        //cara lain dari variabel di atas
        //val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(MainViewModel::class.java)

        mainViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
        }

    }

    @Deprecated("Deprecated in Java")
    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, 0)
    }
}