package me.tatocaster.nasaappchallenge.features.create.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_create.view.*
import me.tatocaster.nasaappchallenge.R
import me.tatocaster.nasaappchallenge.common.utils.showErrorAlert
import me.tatocaster.nasaappchallenge.features.base.BaseFragment
import me.tatocaster.nasaappchallenge.features.home.presentation.HomeActivity
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

    companion object {
        @JvmStatic
        fun newInstance(): CreateFragment = CreateFragment()
    }

}