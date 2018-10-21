package me.tatocaster.nasaappchallenge.features.create.presentation

import android.graphics.Bitmap
import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import me.tatocaster.nasaappchallenge.features.create.usecase.CreateUseCase
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.util.*
import javax.inject.Inject


class CreatePresenter @Inject constructor(private var useCase: CreateUseCase,
                                          private var view: CreateContract.View) : CreateContract.Presenter {

    /*
    REFACTOR THIS TO USECASE LATER!!!
     */

    override fun uploadImageToFirebase(bitmap: Bitmap) {
        val storage = FirebaseStorage.getInstance()
        var storageReference = storage.reference
        val imagesRef = storageReference.child("images/${UUID.randomUUID()}")

        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val uploadTask = imagesRef.putBytes(data)
        val urlTask = uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            return@Continuation imagesRef.downloadUrl
        }).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                view.onImageUploaded(task.result)
            } else {
                view.onImageUploadFailed()
            }
        }
    }

    override fun reportFire(data: HashMap<String, Any>) {
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Timber.e(task.exception)
                        return@addOnCompleteListener
                    }
                    val db = FirebaseFirestore.getInstance()
                    db.collection("posts")
                            .add(data)
                            .addOnSuccessListener { documentReference ->
                                view.onFireReported()
                                Timber.d("DocumentSnapshot added with ID: %s", documentReference.id)
                            }
                            .addOnFailureListener { e -> view.showError(e.message.toString()) }
                }
    }

    override fun onImageCloudLabel(bitmap: Bitmap) {
        //Create a FirebaseVisionImage
        val image = FirebaseVisionImage.fromBitmap(bitmap)

        //Get access to an instance of FirebaseCloudImageDetector
        val detector = FirebaseVision.getInstance().visionCloudLabelDetector

        //Use the detector to detect the labels inside the image
        detector.detectInImage(image)
                .addOnSuccessListener {
                    // Task completed successfully

                    var fireExists = false
                    for(item in it){
                        if(item.label.toLowerCase() == "fire" || item.label.toLowerCase() == "smoke" || item.label.toLowerCase() == "flame"){
                            fireExists = true
                        }
                    }
                    if (!fireExists) {
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
                    var fireExists = false
                    for(item in it){
                        if(item.label.toLowerCase() == "fire" || item.label.toLowerCase() == "smoke" || item.label.toLowerCase() == "flame"){
                            fireExists = true
                        }
                    }
                    if (!fireExists) {
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