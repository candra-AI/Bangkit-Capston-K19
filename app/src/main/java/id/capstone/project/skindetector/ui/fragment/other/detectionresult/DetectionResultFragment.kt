package id.capstone.project.skindetector.ui.fragment.other.detectionresult

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import coil.load
import coil.transform.RoundedCornersTransformation
import id.capstone.project.skindetector.R
import id.capstone.project.skindetector.data.model.DetectResultEntity
import id.capstone.project.skindetector.databinding.FragmentDetectionResultBinding
import id.capstone.project.skindetector.ui.activity.home.HomeActivity.Companion.getRealPathFromURI
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt

class DetectionResultFragment : Fragment() {
    companion object {
        const val IMAGE_GET_KEY = "image_key"
    }

    private var _binding: FragmentDetectionResultBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetectionResultViewModel by viewModel()
    var imagePath: Uri? = null
    var fromGallery = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetectionResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (imagePath != null) {
            viewModel.imagePath = this.imagePath
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)
        val path = if (fromGallery) {
            getRealPathFromURI(getImagePathResult(), requireActivity())
        } else {
            getImagePathResult().path
        } as String

        viewModel.detectImage(path).observe(viewLifecycleOwner) {
            setData(it)
            showLoading(false)
        }

        with(binding) {
            ivResult.load(File(path)) {
                crossfade(true)
                crossfade(1000)
                transformations(RoundedCornersTransformation(30f))
            }
            btnSkip.setOnClickListener {
                requireActivity().supportFragmentManager.commitNow {
                    remove(this@DetectionResultFragment)
                }
            }
        }
    }

    private fun setData(data: DetectResultEntity) {
        with(binding) {
            tvResultDisese1.text = data.firstDisease
            tvResultDisese2.text = data.secondDisease
            tvResultDisese3.text = data.thirdDisease
            pbResult1.progress = data.firstPresentation.roundToInt()
            pbResult2.progress = data.secondPresentation.roundToInt()
            pbResult3.progress = data.secondPresentation.roundToInt()
            tvPrecent1.text =
                getString(
                    R.string.progress_percent,
                    roundOffDecimal(data.firstPresentation).toString()
                )
            tvPrecent2.text =
                getString(
                    R.string.progress_percent,
                    roundOffDecimal(data.secondPresentation).toString()
                )
            tvPrecent3.text =
                getString(
                    R.string.progress_percent,
                    roundOffDecimal(data.thirdPresentation).toString()
                )
        }
    }

    private fun showLoading(show: Boolean) {
        with(binding) {
            if (show) {
                layoutCore.visibility = View.GONE
                pbDetect.visibility = View.VISIBLE
            } else {
                layoutCore.visibility = View.VISIBLE
                pbDetect.visibility = View.GONE
            }
        }
    }

    private fun roundOffDecimal(number: Double): Double {
        val df = DecimalFormat("#.##").apply {
            roundingMode = RoundingMode.CEILING
        }
        return df.format(number).toDouble()
    }

    fun setImagePathResult(uri: Uri?) {
        this.imagePath = uri
    }

    fun getImagePathResult(): Uri =
        if (viewModel.imagePath != null)
            viewModel.imagePath!!
        else
            this.imagePath ?: Uri.EMPTY

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}