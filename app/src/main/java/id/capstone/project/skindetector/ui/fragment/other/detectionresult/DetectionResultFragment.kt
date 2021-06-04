package id.capstone.project.skindetector.ui.fragment.other.detectionresult

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import coil.load
import coil.transform.RoundedCornersTransformation
import id.capstone.project.skindetector.data.model.DetectResultEntity
import id.capstone.project.skindetector.databinding.FragmentDetectionResultBinding
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File
import kotlin.math.roundToInt

class DetectionResultFragment : Fragment() {
    companion object {
        const val IMAGE_GET_KEY = "image_key"
    }
    private var _binding: FragmentDetectionResultBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetectionResultViewModel by viewModel()
    var imagePath: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetectionResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)
        viewModel.detectImage(imagePath?.path.toString()).observe(viewLifecycleOwner) {
            setData(it)
            showLoading(false)
        }

        with(binding) {
            ivResult.load(File(imagePath!!.path)) {
                crossfade(true)
                crossfade(1000)
                transformations(RoundedCornersTransformation(30f))
            }
            btnDone.setOnClickListener {
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
            pbResult1.progress  = data.firstPresentation.roundToInt()
            pbResult2.progress = data.secondPresentation.roundToInt()
            pbResult3.progress = data.secondPresentation.roundToInt()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}