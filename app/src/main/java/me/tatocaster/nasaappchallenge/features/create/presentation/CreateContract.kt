package me.tatocaster.nasaappchallenge.features.create.presentation

import android.graphics.Bitmap
import android.net.Uri

interface CreateContract {
    interface View {
        fun showError(message: String)

        fun clearImageViewToDefault()

        fun onFireReported()

        fun onImageUploaded(uri: Uri?)

        fun onImageUploadFailed()
    }

    interface Presenter {
        fun onImageLabel(bitmap: Bitmap)
        fun onImageCloudLabel(bitmap: Bitmap)
        fun uploadImageToFirebase(bitmap: Bitmap)
        fun reportFire(data: HashMap<String, Any>)
        fun detach()
    }
}