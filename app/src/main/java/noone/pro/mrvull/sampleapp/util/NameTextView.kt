package noone.pro.mrvull.sampleapp.util

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import noone.pro.mrvull.sampleapp.util.function.inchesOfScreen


class NameTextView : TextView {


    constructor(context: Context) : super(context) {
        this.textSize = inchesOfScreen(context) + NAME_TEXT_SIZE
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.textSize = inchesOfScreen(context) + NAME_TEXT_SIZE
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        this.textSize = inchesOfScreen(context) + NAME_TEXT_SIZE
    }

    companion object {
        const val NAME_TEXT_SIZE = 15
    }
}