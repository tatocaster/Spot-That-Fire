package me.tatocaster.nasaappchallenge.features.create.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_create.view.*
import me.tatocaster.nasaappchallenge.MAP_ACTIVITY_REQUEST_CODE
import me.tatocaster.nasaappchallenge.R
import me.tatocaster.nasaappchallenge.common.utils.showErrorAlert
import me.tatocaster.nasaappchallenge.features.base.BaseFragment
import me.tatocaster.nasaappchallenge.features.home.presentation.HomeActivity
import me.tatocaster.nasaappchallenge.features.map.presentation.MapsActivity
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class CreateFragment : BaseFragment(), CreateContract.View {
    @Inject
    lateinit var presenter: CreateContract.Presenter

    private lateinit var homeActivity: HomeActivity

    private lateinit var root: View

    private var imagePreviewBitmap: Bitmap? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_create, container, false)

        homeActivity = activity as HomeActivity

        root.finishCameraKit.setOnClickListener {
            root.progressBar.visibility = View.VISIBLE
            showPreview()

            root.cameraView.captureImage { cameraKitImage ->
                // Get the Bitmap from the captured shot
                presenter.onImageLabel(cameraKitImage.bitmap)
//                presenter.onImageCloudLabel(cameraKitImage.bitmap)
                homeActivity.runOnUiThread {
                    showPreview()
                    root.imagePreview.setImageBitmap(cameraKitImage.bitmap)
                    root.progressBar.visibility = View.GONE
                    imagePreviewBitmap = cameraKitImage.bitmap
                }
            }
        }

        root.snapPic.setOnClickListener {
            hidePreview()
        }

        root.mapsButtonWrapper.setOnClickListener {
            val i = Intent(homeActivity, MapsActivity::class.java)
            startActivityForResult(i, MAP_ACTIVITY_REQUEST_CODE)
        }

        root.call.setOnClickListener {
            if (ContextCompat.checkSelfPermission(homeActivity, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:112"))
                startActivity(intent)
            }
        }

        root.reportFire.setOnClickListener { btn ->
            disableView()
            imagePreviewBitmap?.let {
                presenter.uploadImageToFirebase(it)
            }
        }

        return root
    }

    override fun onImageUploadFailed() {
        onImageUploaded(null)
    }

    override fun onFireReported() {
        enableView()
        homeActivity.navigateToHome()
    }

    override fun onImageUploaded(uri: Uri?) {
        val data: HashMap<String, Any> = hashMapOf()
        val juDate = Date()
        val dt = DateTime(juDate)
        val dateFormatter = SimpleDateFormat("EEEE dd MMMM yyyy", Locale.getDefault())
        data["created_at"] = dateFormatter.format(dt.toLocalDateTime().toString())
        data["description"] = root.descriptionInput.text.toString()

        if (root.locationTextView.text != null) {
            data["lat"] = root.locationTextView.text.toString().split(":")[0].toDouble()
            data["lng"] = root.locationTextView.text.toString().split(":")[1].toDouble()
        }

        if (uri != null)
            data["image"] = uri
        presenter.reportFire(data)
    }

    private fun disableView() {
        root.reportFire.isClickable = false
        root.reportFire.isEnabled = false
        root.descriptionInput.isEnabled = false
        root.locationTextView.isEnabled = false
        root.uploadProgressBar.visibility = View.VISIBLE
    }

    private fun enableView() {
        root.reportFire.isClickable = true
        root.reportFire.isEnabled = true
        root.descriptionInput.isEnabled = true
        root.locationTextView.isEnabled = true
        root.uploadProgressBar.visibility = View.GONE
    }

    override fun showError(message: String) {
        showErrorAlert(homeActivity, message, getString(R.string.try_again))
        enableView()
    }

    override fun onResume() {
        super.onResume()
        root.cameraView.start()
    }

    override fun onPause() {
        root.cameraView.stop()
        super.onPause()
    }

    override fun onDestroyView() {
        presenter.detach()
        super.onDestroyView()
    }

    private fun showPreview() {
        root.layoutWrapper.visibility = View.VISIBLE
        root.cameraWrapper.visibility = View.GONE
    }

    private fun hidePreview() {
        root.layoutWrapper.visibility = View.GONE
        root.cameraWrapper.visibility = View.VISIBLE

        val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)

    }

    override fun clearImageViewToDefault() {
        root.imagePreview.setImageBitmap(null)
        root.imagePreview.setImageResource(R.drawable.ic_photo_library)
        imagePreviewBitmap = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == MAP_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val result = data!!.getBundleExtra("result")
                val latLangFromBundle = result.getParcelable<LatLng?>("latlng")
                latLangFromBundle?.let {
                    root.locationTextView.text = "${it.latitude} : ${it.longitude}"
                }

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): CreateFragment = CreateFragment()
    }

}