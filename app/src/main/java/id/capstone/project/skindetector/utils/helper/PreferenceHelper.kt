package id.capstone.project.skindetector.utils.helper

import android.content.Context
import androidx.core.content.edit
import id.fadillah.jetpacksubmission.R

internal class PreferenceHelper(private val context: Context) {
    private val preference = context.getSharedPreferences(context.getString(R.string.preference_name), Context.MODE_PRIVATE)

    fun setShowed() {
        preference.edit {
            putBoolean(context.getString(R.string.preference_key), true)
        }
    }

    fun isShowed(): Boolean = preference.getBoolean(context.getString(R.string.preference_key), false)
}