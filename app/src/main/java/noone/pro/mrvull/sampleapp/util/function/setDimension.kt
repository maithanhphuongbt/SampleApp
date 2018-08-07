package noone.pro.mrvull.sampleapp.util.function

import android.view.View

fun setDimension(view: View, width: Float, height: Float) {
    val params = view.layoutParams
    params.width = width.toInt()
    params.height = height.toInt()
    view.layoutParams = params
}