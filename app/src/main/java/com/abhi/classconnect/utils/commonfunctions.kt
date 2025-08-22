package com.abhi.classconnect.utils

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

fun createImageUri(context: Context): Uri {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_${System.currentTimeMillis()}.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }
    }
    return context.contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        contentValues
    )!!
}



@OptIn(ExperimentalTime::class)
 fun  currentTimeInMillis() = Clock.System.now().toEpochMilliseconds()


@RequiresApi(Build.VERSION_CODES.O)
fun toDate(millis : Long) : String{
    val selectedLocalDate = Instant.ofEpochMilli(millis)
        .atZone(ZoneId.systemDefault()).toLocalDate()
    return selectedLocalDate.toString()
}