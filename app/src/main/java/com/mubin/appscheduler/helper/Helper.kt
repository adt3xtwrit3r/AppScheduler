package com.mubin.appscheduler.helper

import android.app.ProgressDialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Base64
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mubin.appscheduler.R
import java.io.ByteArrayOutputStream


fun Fragment.progressDialog(message: String = getString(R.string.waiting)): ProgressDialog {

    val dialog = ProgressDialog(requireContext())
    with(dialog) {
        setMessage(message)
    }
    return dialog
} // for showing progress Dialog // deprecated :( but my favourite

fun Context.toast(msg: String?, time: Int = Toast.LENGTH_SHORT) {
    if (!msg.isNullOrEmpty()) {
        val toast = Toast.makeText(this, msg, time)
        val view: View? = toast.view
        view?.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black_100))
        val textView: TextView? = view?.findViewById(android.R.id.message)
        textView?.setTextColor(ContextCompat.getColor(this, R.color.white))
        toast.show()
    }

} // for showing toasts

fun Fragment.alert(
    title: CharSequence? = null,
    message: CharSequence? = null,
    showCancel: Boolean = false,
    positiveButtonText: String = "Okay",
    negativeButtonText: String = "Cancel",
    listener: ((type: Int) -> Unit)? = null
): AlertDialog {

    val builder = MaterialAlertDialogBuilder(requireContext())
    builder.setTitle(title)
    // Display a message on alert dialog
    builder.setMessage(message)
    // Set a positive button and its click listener on alert dialog
    builder.setPositiveButton(positiveButtonText) { dialog, which ->
        dialog.dismiss()
        listener?.invoke(AlertDialog.BUTTON_POSITIVE)
    }
    // Display a negative button on alert dialog
    if (showCancel) {
        builder.setNegativeButton(negativeButtonText) { dialog, which ->
            dialog.dismiss()
            listener?.invoke(AlertDialog.BUTTON_NEGATIVE)
        }
    }

    val textView: TextView? = view?.findViewById(android.R.id.message)
    textView?.textSize = 12F

    return builder.create()
} // For showing Alert dialog

fun encodeToBase64(image: Bitmap, compressFormat: CompressFormat?, quality: Int): String? {

    val byteArrayOS = ByteArrayOutputStream()
    image.compress(compressFormat, quality, byteArrayOS)
    return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.NO_WRAP)

} // bitmap to Base64 conversion

fun drawableToBitmap(icon: Drawable): Bitmap {

    val bitmapDrawable: BitmapDrawable = (icon as BitmapDrawable)

    return bitmapDrawable.bitmap

} // drawable to bitmap conversion  // works for old android versions

fun getBitmapFromDrawable(drawable: Drawable): Bitmap {
    val bmp = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bmp)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bmp
} // drawable to bitmap conversion

fun base64ToImage(icon64: String): Bitmap {

    val imageBytes = Base64.decode(icon64, 0)

    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

} // Base64 to bitmap conversion