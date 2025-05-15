package dev.vincenzocostagliola.data.datapersistence

import android.content.Context
import android.content.SharedPreferences

private const val SP_PREFERENCE_NAME = "DodoSP"


internal fun Context.createSharedPref(): SharedPreferences = this.getSharedPreferences(SP_PREFERENCE_NAME, 0)