package com.nolawiworkineh.core.data.auth

import android.content.SharedPreferences
import com.nolawiworkineh.core.domain.AuthInfo.AuthInfo
import com.nolawiworkineh.core.domain.SessionStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

// **EncryptedSessionStorage**: A class that securely stores and retrieves authentication info using SharedPreferences.
class EncryptedSessionStorage(
    // **SharedPreferences**: Used to store the serialized AuthInfo data in a secure way.
    private val sharedPreferences: SharedPreferences,
): SessionStorage {

    // **get()**: Retrieves and decrypts the stored AuthInfo if available.
    override suspend fun get(): AuthInfo? {
        return withContext(Dispatchers.IO) {  // Run the storage operation in the IO thread
            val json = sharedPreferences.getString(KEY_AUTH_INFO, null)  // Retrieve the stored JSON
            json?.let {
                // Deserialize the JSON back into AuthInfoSerializable, then map it to AuthInfo
                Json.decodeFromString<AuthInfoSerializable>(it).toAuthInfo()
            }
        }
    }

    // **set(info)**: Encrypts and stores the provided AuthInfo.
    override suspend fun set(info: AuthInfo?) {
        withContext(Dispatchers.IO) {  // Run the storage operation in the IO thread
            if (info == null) {
                // Remove AuthInfo if null is provided (e.g., logging out the user)
                sharedPreferences.edit().remove(KEY_AUTH_INFO).commit()
                return@withContext
            }
            // Serialize AuthInfo into a JSON string
            val json = Json.encodeToString(info.toAuthInfoSerializable())
            // Store the JSON in SharedPreferences
            sharedPreferences
                .edit()
                .putString(KEY_AUTH_INFO, json)
                .commit()  // Apply the changes synchronously
        }
    }

    // **companion object**: Defines a constant key used to store the AuthInfo in SharedPreferences.
    companion object {
        private const val KEY_AUTH_INFO = "KEY_AUTH_INFO"
    }
}
