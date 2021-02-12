package com.example.core.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.core.R
import com.example.core.utils.Constants.Companion.BASE_IMG
import com.example.core.utils.Constants.Companion.CROSSFADE_DURATION

class BindingAdapter {
    companion object {
        @BindingAdapter("imgUrl")
        @JvmStatic
        fun bindImage(imageView: ImageView, imgUrl: String?) {
            imageView.load(BASE_IMG + imgUrl) {
                crossfade(CROSSFADE_DURATION)
                error(R.drawable.ic_error_img)
            }
        }

    }
}

