package org.ligi.survivalmanual.model

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import org.ligi.survivalmanual.R

private const val DEFAULT_FONT_SIZE_STRING = "2"

object State {

    val FALLBACK_URL = navigationEntryMap.first().entry.url

    fun init(context: Context) {
        this.resources = context.resources
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    private lateinit var resources: Resources

    private lateinit var sharedPreferences: SharedPreferences

    var lastVisitedURL: String
        get() = sharedPreferences.getString(
            R.string.preference_key_lastVisitedURL.string(),
            FALLBACK_URL
        )!!
        set(value) {
            sharedPreferences.edit {
                this.putString(R.string.preference_key_lastVisitedURL.string(), value)
            }
        }

    var searchTerm: String?
        get() = sharedPreferences.getString(R.string.preference_key_searchTerm.string(), null)
        set(value) {
            sharedPreferences.edit {
                this.putString(R.string.preference_key_lastScrollPos.string(), value)
            }
        }

    var lastScrollPos: Int
        get() = sharedPreferences.getInt(R.string.preference_key_lastScrollPos.string(), 0)
        set(value) {
            sharedPreferences.edit {
                this.putInt(R.string.preference_key_lastScrollPos.string(), value)
            }
        }
    var isInitialOpening: Boolean
        get() = sharedPreferences.getBoolean(
            R.string.preference_key_isInitialOpening.string(),
            false
        )
        set(value) {
            sharedPreferences.edit {
                this.putBoolean(R.string.preference_key_isInitialOpening.string(), value)
            }
        }

    fun allowEdit() =
        sharedPreferences.getBoolean(R.string.preference_key_edittoggle.string(), false)

    fun allowSelect() =
        sharedPreferences.getBoolean(R.string.preference_key_select_text.string(), false)

    fun getFontSize() = 8f + 4f * Integer.parseInt(getFontSizeString())

    private fun getFontSizeString() =
        sharedPreferences.getString(
            R.string.preference_key_fontsize.string(),
            DEFAULT_FONT_SIZE_STRING
        )!!

    fun nightModeString(): String =
        sharedPreferences.getString(R.string.preference_key_nightmode.string(), "system")!!

    fun getNightMode() = when (nightModeString()) {
        "day" -> AppCompatDelegate.MODE_NIGHT_NO
        "night" -> AppCompatDelegate.MODE_NIGHT_YES
        "darknight" -> AppCompatDelegate.MODE_NIGHT_YES
        "system" -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }

    fun Int.string() = resources.getString(this)

    fun applyDayNightMode() {
        AppCompatDelegate.setDefaultNightMode(getNightMode())
    }
}
