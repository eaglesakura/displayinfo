package io.github.eaglesakura.displayinfo

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Parcelable
import android.view.Display
import android.view.WindowManager
import kotlin.math.min
import kotlinx.parcelize.Parcelize

/**
 * This object calculate display information this device.
 *
 * e.g.)
 * val info = DisplayInfo.newInstance(context)
 * Log.d("DisplayInfo", "${info.widthPixel} x ${info.heightPixel}")
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/displayinfo
 */
@Suppress("MemberVisibilityCanBePrivate")
@Parcelize
data class DisplayInfo(
    /**
     * Display horizontal pixels
     */
    val widthPixel: Int,

    /**
     * Display vertical pixels
     */
    val heightPixel: Int,

    val deviceType: DeviceType,

    /**
     * Display horizontal dp size.
     */
    val widthDp: Float,

    /**
     * Display vertical dp size.
     */
    val heightDp: Float,

    val widthInch: Float,

    val heightInch: Float,

    /**
     * Display-size with round for user.
     * Rounding off the 2nd decimal place.
     *
     * example.
     * 4.65 inch display, returns "4.7".
     * Inch(major=4, minor=7)
     */
    val diagonalRoundInch: Inch,

    /**
     * Dot per inch.
     */
    val dpi: DPI,

    /**
     * values-sw${smallestWidthDp}dp
     */
    val smallestWidthDp: Int,

    val diagonalInch: Float
) : Parcelable {

    companion object {
        @JvmStatic
        fun newInstance(context: Context): DisplayInfo {
            val display: Display = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                try {
                    context.display!!
                } catch (e: Exception) {
                    (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
                }
            } else {
                (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            }

            val displayMetrics = context.resources.displayMetrics
            val widthPixel: Int
            val heightPixel: Int
            val point = Point(0, 0)
            display.getRealSize(point)
            widthPixel = point.x
            heightPixel = point.y

            val widthDp = widthPixel.toFloat() / displayMetrics.density
            val heightDp = heightPixel.toFloat() / displayMetrics.density

            val widthInch = widthPixel.toFloat() / displayMetrics.xdpi
            val heightInch = heightPixel.toFloat() / displayMetrics.ydpi
            val dpi = DPI.parse(displayMetrics.xdpi, displayMetrics.ydpi)

            val diagonalInch =
                Math.sqrt((widthInch * widthInch + heightInch * heightInch).toDouble()).toFloat()
            val diagonal = (diagonalInch * 100.0f).toInt().let { (it + 5) / 10 }
            val diagonalRoundInch = Inch(diagonal / 10, diagonal % 10)

            // 丸められたインチ数で、デバイスを種類を指定する
            val deviceType = when {
                diagonalRoundInch.major <= 5 -> DeviceType.Phone
                diagonalRoundInch.major <= 6 -> DeviceType.Phablet
                diagonalRoundInch.major <= 12 -> DeviceType.Tablet
                else -> DeviceType.Other
            }

            val smallestWidthDp = min(widthDp, heightDp).toInt() / 10 * 10

            return DisplayInfo(
                widthPixel = widthPixel,
                heightPixel = heightPixel,
                widthDp = widthDp,
                heightDp = heightDp,
                widthInch = widthInch,
                heightInch = heightInch,
                deviceType = deviceType,
                smallestWidthDp = smallestWidthDp,
                dpi = dpi,
                diagonalInch = diagonalInch,
                diagonalRoundInch = diagonalRoundInch
            )
        }
    }
}
