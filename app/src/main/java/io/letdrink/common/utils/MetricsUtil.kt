package io.letdrink.common.utils

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.Display


object MetricsUtil {

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics. If you don't have
     * access to Context, just pass null.
     * @return A float value to represent px equivalent to dp depending on device density
     */
    fun convertDpToPixel(context: Context, dp: Float): Float {
        return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics. If you don't have
     * access to Context, just pass null.
     * @return A float value to represent dp equivalent to px value
     */
    fun convertPixelsToDp(context: Context, px: Float): Float {
        return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun Context.convertSpToPixeils(sp: Int): Float {
        return sp * (resources.displayMetrics.scaledDensity)
    }

    fun getDisplayWH(activity: Activity): Pair<Int, Int> {
        val display: Display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width: Int = size.x
        val height: Int = size.y
        return width to height
    }

    fun getDisplayH(activity: Activity): Int {
        return getDisplayWH(activity).second
    }
}