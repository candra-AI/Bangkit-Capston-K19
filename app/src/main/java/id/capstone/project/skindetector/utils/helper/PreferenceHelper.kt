package id.capstone.project.skindetector.utils.helper

import android.content.Context
import androidx.core.content.edit
import id.capstone.project.skindetector.R

internal class PreferenceHelper(private val context: Context) {
    private val preference = context.getSharedPreferences(
        context.getString(R.string.preference_name),
        Context.MODE_PRIVATE
    )

    fun setLogin() {
        preference.edit {
            putBoolean(context.getString(R.string.preference_key), true)
        }
    }

    fun isLoggedIn(): Boolean =
        preference.getBoolean(context.getString(R.string.preference_key), false)
}