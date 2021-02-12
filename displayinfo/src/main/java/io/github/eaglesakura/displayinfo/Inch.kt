package io.github.eaglesakura.displayinfo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Display inch
 */
@Parcelize
data class Inch(val major: Int, val minor: Int) : Parcelable {

    @Suppress("unused")
    fun toFloat(): Float {
        return major.toFloat() + (minor.toFloat() / 10.0f)
    }

    override fun toString(): String {
        return "$major.$minor"
    }
}
