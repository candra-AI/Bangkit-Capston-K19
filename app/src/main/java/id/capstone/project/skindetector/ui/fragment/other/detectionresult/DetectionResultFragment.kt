package id.capstone.project.skindetector.ui.fragment.other.detectionresult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import id.capstone.project.skindetector.R
import id.capstone.project.skindetector.data.model.DetectResultEntity
import id.capstone.project.skindetector.databinding.FragmentDetectionResultBinding
import id.capstone.project.skindetector.ui.activity.home.HomeActivity.Companion.getRealPathFromURI
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
    private lateinit var storage: FirebaseStorage
    private val firebaseCrashlytics: FirebaseCrashlytics by lazy { FirebaseCrashlytics.getInstance() }
    private val args: DetectionResultFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storage = Firebase.storage
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetectionResultBinding.inflate(inflater, container, false)
        firebaseCrashlytics.sendUnsentReports()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        firebaseCrashlytics.sendUnsentReports()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)
        val path = if (!args.isFromCamera) {
            getRealPathFromURI(args.uriImage, requireActivity())
        } else {
            args.uriImage.path
        } as String

        viewModel.detectImage(path).observe(viewLifecycleOwner) {
            setData(it)
            showLoading(false)
            viewModel.uploadFiles(storage, path)
        }

        with(binding) {
            ivResult.load(File(path)) {
                crossfade(true)
                crossfade(1000)
                transformations(RoundedCornersTransformation(30f))
            }
            btnSkip.setOnClickListener {
                findNavController().navigate(DetectionResultFragmentDirections.actionNavigationResultToNavigationCamera())
            }
            btnValidity.setOnClickListener {
                findNavController().navigate(R.id.navigation_premium)
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
                layoutLottie.visibility = View.VISIBLE
            } else {
                layoutCore.visibility = View.VISIBLE
                layoutLottie.visibility = View.GONE
            }
        }
    }

    private fun roundOffDecimal(number: Double): Double {
        val number3digits: Double = (number * 1000.0).roundToInt() / 1000.0
        return (number3digits * 100.0).roundToInt() / 100.0
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}