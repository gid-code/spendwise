package com.gidcode.spendwise.data.repository

import com.gidcode.spendwise.data.di.PreferencesModule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules

//import org.junit.jupiter.api.Assertions.*

@HiltAndroidTest
@UninstallModules(PreferencesModule::class)
class AuthDataRepositoryTest