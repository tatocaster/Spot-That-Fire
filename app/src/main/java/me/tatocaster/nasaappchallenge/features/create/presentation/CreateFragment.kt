package me.tatocaster.nasaappchallenge.features.create.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_create.view.*
import me.tatocaster.nasaappchallenge.MAP_ACTIVITY_REQUEST_CODE
import me.tatocaster.nasaappchallenge.R
import me.tatocaster.nasaappchallenge.common.utils.showErrorAlert
import me.tatocaster.nasaappchallenge.features.base.BaseFragment
import me.tatocaster.nasaappchallenge.features.home.presentation.HomeActivity
import me.tatocaster.nasaappchallenge.features.map.presentation.MapsActivity
import javax.inject.Inject


class CreateFragment : BaseFragment(), CreateContract.View {
    @Inject
    lateinit var presenter: CreateContract.Presenter

    private lateinit var homeActivity: HomeActivity

    private lateinit var root: View

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

        return root
    }

    override fun showError(message: String) {
        showErrorAlert(homeActivity, message, getString(R.string.try_again))
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
    }

    override fun clearImageViewToDefault() {
        root.imagePreview.setImageBitmap(null)
        root.imagePreview.setImageResource(R.drawable.ic_photo_library)
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