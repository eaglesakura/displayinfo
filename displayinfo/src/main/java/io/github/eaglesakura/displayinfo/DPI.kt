package io.github.eaglesakura.displayinfo

import kotlin.math.min

@Suppress("EnumEntryName")
enum class DPI {
    ldpi,
    mdpi,
    tvdpi,
    hdpi,
    xhdpi,
    xxhdpi,
    xxxhdpi;

    companion object {
        internal fun parse(xdpi: Float, ydpi: Float): DPI {
            val dpi = min(xdpi, ydpi)

            return when {
                dpi > 480 -> DPI.xxxhdpi
                dpi > 320 -> DPI.xxhdpi
                dpi > 240 -> DPI.xhdpi
                dpi > 210 -> DPI.tvdpi
                dpi > 160 -> DPI.hdpi
                dpi > 120 -> DPI.mdpi
                else -> DPI.ldpi
            }
        }
    }
}
