package id.capstone.project.skindetector.ui.fragment.other.detectionresult

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import id.capstone.project.skindetector.databinding.FragmentDetectionResultBinding

class DetectionResultFragment : Fragment() {
    companion object {
        const val IMAGE_GET_KEY = "image_key"
    }
    private var _binding: FragmentDetectionResultBinding? = null
    private val binding get() = _binding!!
    var image: Bitmap? = null

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
//        val byteArray = arguments?.getByteArray(IMAGE_GET_KEY) as ByteArray
//        val image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

        with(binding) {
            ivResult.load(image)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}