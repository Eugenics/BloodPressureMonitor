package com.eugenics.bloodpressuremonitor.domain.models

import android.util.Log
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import java.lang.Exception

@Serializable
data class UserSettings(
    val themeSetting: Theme = Theme.DARK
)

enum class Theme {
    SYSTEM,
    LIGHT,
    DARK
}

object UserSettingsSerializer : Serializer<UserSettings> {
    override val defaultValue: UserSettings = UserSettings()

    override suspend fun readFrom(input: InputStream): UserSettings =
        try {
            Json.decodeFromString(
                UserSettings.serializer(),
                input.readBytes().decodeToString()
            )
        } catch (serializationException: SerializationException) {
            Log.d("Decode from JSON", serializationException.message.toString())
            throw CorruptionException("Unable to read UserPrefs", serializationException)
        }

    override suspend fun writeTo(t: UserSettings, output: OutputStream) {
        try {
            output.write(
                Json.encodeToString(UserSettings.serializer(), t).encodeToByteArray()
            )
        } catch (e: Exception) {
            Log.d("Encode to JSON", e.message.toString())
        }
    }

}
