package com.example.naseem.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

object DrawableHelper {
    fun resizeDrawable(context: Context, drawableRes: Int, width: Int, height: Int, tint: Int): Drawable {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val drawable = ContextCompat.getDrawable(context, drawableRes)!!.mutate()
        drawable.setTint(tint)
        drawable.setBounds(0, 0, width, height)
        drawable.draw(canvas)
        return BitmapDrawable(context.resources, bitmap)
    }
}