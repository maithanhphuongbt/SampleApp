package noone.pro.mrvull.sampleapp.util.function

import android.content.Context

fun inchesOfScreen(context: Context): Float {
    return ((Math.sqrt((getWidthDevice(context)
            + getHeightDevice(context)).toDouble())) / 10).toFloat()
}