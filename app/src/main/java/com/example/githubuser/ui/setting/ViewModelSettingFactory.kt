package com.example.githubuser.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import java.lang.IllegalArgumentException

class ViewModelSettingFactory(private val pref: SettingPreferences) : NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelSetting::class.java)) {
            return ViewModelSetting(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class" + modelClass.name)
    }
}