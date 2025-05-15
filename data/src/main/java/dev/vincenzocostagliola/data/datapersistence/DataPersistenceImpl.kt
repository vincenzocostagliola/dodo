package dev.vincenzocostagliola.data.datapersistence

import kotlinx.serialization.json.Json
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


internal class DataPersistenceImpl(
    private val sp: DataPersistenceSP
) : DataPersistence {
    private val settingsConfiguration = "SETTING_CONF"

    override suspend fun getSettings(): SettingsDP? {
        Timber.d("DATAPERSISTENCE - getSettings")
        return suspendCoroutine { continuation ->
            val result = runCatching {
                val json = sp.sharedPreferences.getString(settingsConfiguration, null)
                Timber.d("DATAPERSISTENCE - getDebugSettings: $json")
                json?.let { Json.decodeFromString<SettingsDP?>(it) }
            }

            result.fold(
                onSuccess = {
                    Timber.d("DATAPERSISTENCE - getSettings -  $it")
                    continuation.resume(it)
                },
                onFailure = {
                    Timber.e("DATAPERSISTENCE - getSettings - exception: $it")
                    continuation.resume(null)
                }
            )
        }
    }

}
