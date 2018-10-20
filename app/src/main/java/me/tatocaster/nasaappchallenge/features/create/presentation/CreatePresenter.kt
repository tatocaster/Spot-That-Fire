package me.tatocaster.nasaappchallenge.features.create.presentation

import android.graphics.Bitmap
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import me.tatocaster.nasaappchallenge.features.create.usecase.CreateUseCase
import javax.inject.Inject

class CreatePresenter @Inject constructor(private var useCase: CreateUseCase,
                                          private var view: CreateContract.View) : CreateContract.Presenter {

    /*
    REFACTOR THIS TO USECASE LATER!!!
     */

    override fun onImageCloudLabel(bitmap: Bitmap) {
        //Create a FirebaseVisionImage
        val image = FirebaseVisionImage.fromBitmap(bitmap)

        //Get access to an instance of FirebaseCloudImageDetector
        val detector = FirebaseVision.getInstance().visionCloudLabelDetector

        //Use the detector to detect the labels inside the image
        detector.detectInImage(image)
                .addOnSuccessListener {
                    // Task completed successfully
                    val filteredData = it.filter {
                        it.label.toLowerCase() == "fire" || it.label.toLowerCase() == "smoke"
                    }
                    if (filteredData.isEmpty()) {
                        view.showError("Image does not contain any fire!")
                        view.clearImageViewToDefault()
                    }
                }
                .addOnFailureListener {
                    // Task failed with an exception
                    view.showError("Sorry, something went wrong!")
                    view.clearImageViewToDefault()
                }
    }

    override fun onImageLabel(bitmap: Bitmap) {
        //Create a FirebaseVisionImage
        val image = FirebaseVisionImage.fromBitmap(bitmap)

        //Get access to an instance of FirebaseImageDetector
        val detector = FirebaseVision.getInstance().visionLabelDetector

        //Use the detector to detect the labels inside the image
        detector.detectInImage(image)
                .addOnSuccessListener {
                    // Task completed successfully
                    val filteredData = it.filter {
                        it.label.toLowerCase() == "fire" || it.label.toLowerCase() == "smoke"
                    }
                    if (filteredData.isEmpty()) {
                        view.showError("Image does not contain any fire!")
                        view.clearImageViewToDefault()
                    }

                }
                .addOnFailureListener {
                    // Task failed with an exception
                    view.showError("Sorry, something went wrong!")
                    view.clearImageViewToDefault()
                }
    }

    override fun detach() {
        useCase.clear()
    }

}