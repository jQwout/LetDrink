package io.letdrink.common.utils

import android.view.View
import android.widget.TextView

fun View.visibleOrInvisible(visible: Boolean) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}

fun View.visibleOrGone(visible: Boolean) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun TextView.setTextOrGone(text: String?){
    visibleOrGone(text != null)
    setText(text)
}