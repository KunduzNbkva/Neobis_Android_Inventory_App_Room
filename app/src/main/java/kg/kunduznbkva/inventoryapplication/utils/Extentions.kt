package kg.kunduznbkva.inventoryapp.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

fun View.loadImage(res: Int){
    Glide
        .with(this)
        .load(res)
        .apply(RequestOptions.bitmapTransform( RoundedCorners(14)))
        .into(this as ImageView)
}
