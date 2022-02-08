package ru.dtx12.coolblue.core.extensions

import android.view.View

fun View.visible(visible: Boolean) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}