package com.abhi.classconnect.utils.extensions

fun String.toDisplayText() : String{
    return if (this.length > 10) {
        this.take(10) + "..."
    } else {
        this
    }
}
