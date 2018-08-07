package noone.pro.mrvull.sampleapp.util.function

import android.content.Context

fun getHeightDevice(context: Context): Float {
    return context.resources.displayMetrics.heightPixels.toFloat()
}