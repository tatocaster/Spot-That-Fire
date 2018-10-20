package me.tatocaster.nasaappchallenge.features.create.presentation

import android.graphics.Bitmap

interface CreateContract {
    interface View {
        fun showError(message: String)

        fun clearImageViewToDefault()
    }

    interface Presenter {
        fun onImageLabel(bitmap: Bitmap)
        fun onImageCloudLabel(bitmap: Bitmap)
        fun detach()
    }
}