package io.letdrink.common.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

/**
 * @author ivangolovacev
 */
interface KeyValueStorage {

    fun getAll(): List<String>

    fun get(key: String): String?

    fun put(key: String, value: String?)

    fun delete(key: String)
}

class KeyValueStorageImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : KeyValueStorage {

    override fun getAll(): List<String> {
        return sharedPreferences.all.values
            .filterNotNull()
            .map { it.toString() }
            .toList()
    }

    override fun get(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    override fun put(key: String, value: String?) {
        sharedPreferences.edit {
            this.putString(key, value)
        }
    }

    override fun delete(key: String) {
        sharedPreferences.edit {
            this.remove(key)
        }
    }
}
