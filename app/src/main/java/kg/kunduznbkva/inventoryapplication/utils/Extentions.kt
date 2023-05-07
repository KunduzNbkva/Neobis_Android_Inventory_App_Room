package kg.kunduznbkva.inventoryapplication.utils

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

fun View.loadImage(res: Bitmap){
    Glide
        .with(this)
        .asBitmap()
        .load(res)
        .apply(RequestOptions.bitmapTransform( RoundedCorners(14)))
        .into(this as ImageView)
}

fun Context.showAlertDialog(
    title: String? = null,
    message: String? = null,
    positiveButtonTitle: String? = null,
    positiveButtonAction: (() -> Unit)? = null,
    negativeButtonTitle: String? = null,
    negativeButtonAction: (() -> Unit)? = null,
    neutralButtonTitle: String? = null,
    neutralButtonAction: (() -> Unit)? = null,
    cancelable: Boolean = true
) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setCancelable(cancelable)

    positiveButtonTitle?.let {
        builder.setPositiveButton(it) { _, _ ->
            positiveButtonAction?.invoke()
        }
    }

    negativeButtonTitle?.let {
        builder.setNegativeButton(it) { _, _ ->
            negativeButtonAction?.invoke()
        }
    }

    neutralButtonTitle?.let {
        builder.setNeutralButton(it) { _, _ ->
            neutralButtonAction?.invoke()
        }
    }

    builder.show()

}