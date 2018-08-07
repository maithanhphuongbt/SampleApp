package noone.pro.mrvull.sampleapp.util.function

import android.content.Context

fun getWidthDevice(context: Context): Float {
    return context.resources.displayMetrics.widthPixels.toFloat()
}