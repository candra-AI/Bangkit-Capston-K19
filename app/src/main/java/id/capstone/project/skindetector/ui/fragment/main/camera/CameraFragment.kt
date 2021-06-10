package id.capstone.project.skindetector.ui.fragment.main.camera

import android.Manifest.permission.*
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.crashlytics.FirebaseCrashlytics
import id.capstone.project.skindetector.R
import id.capstone.project.skindetector.databinding.FragmentCameraBinding
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


/**
 * A simple [Fragment] subclass.
 * Use the [CameraFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CameraFragment : Fragment() {
    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        const val REQUEST_CODE_PERMISSIONS = 10
        const val GALLERY_REQUEST = 156
        val REQUIRED_PERMISSIONS = arrayOf(CAMERA, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE)
    }

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!
    private var imageCapture: ImageCapture? = null
    private var flashState = false
    private val viewModel: CameraViewModel by viewModel()
    private val firebaseCrashlytics: FirebaseCrashlytics by lazy { FirebaseCrashlytics.getInstance() }

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        firebaseCrashlytics.sendUnsentReports()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        if (!Camera.getCameraInfo()) {
//            Toast.makeText(context, "Device doesn't support Flash!", Toast.LENGTH_SHORT).show()
//        }

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                context as Activity, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        // Set up the listener for take photo button
        with(binding) {
            btnTakePicture.setOnClickListener { takePhoto() }
            btnGallery.setOnClickListener {
                val photoPickerIntent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                activity?.startActivityForResult(
                    Intent.createChooser(photoPickerIntent, "Select Picture To Detect"),
                    GALLERY_REQUEST
                )
            }
            btnFlash.setOnClickListener {
                if (flashState) {
                    imageCapture?.flashMode = ImageCapture.FLASH_MODE_OFF
                } else {
                    imageCapture?.flashMode = ImageCapture.FLASH_MODE_ON
                }
                flashState = !flashState
            }
        }

        outputDirectory = getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time-stamped output file to hold the image
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME_FORMAT, Locale.US
            ).format(System.currentTimeMillis()) + ".jpg"
        )

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    val msg = "Photo capture failed: ${exc.message}"
                    Log.e(TAG, msg, exc)
                    firebaseCrashlytics.log(msg)
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo capture succeeded: $savedUri"
                    Log.d(TAG, msg)

                    val action =
                        CameraFragmentDirections.actionNavigationCameraToNavigationResult(
                            savedUri
                        ).apply {
                            isFromCamera = true
                        }
                    findNavController().navigate(action)
                }
            })
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }
            imageCapture = ImageCapture.Builder()
                .build()

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )

            } catch (exc: Exception) {
                firebaseCrashlytics.recordException(exc)
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getOutputDirectory(): File {
        val mediaDir = context?.externalMediaDirs?.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else requireContext().filesDir
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
        firebaseCrashlytics.sendUnsentReports()
        _binding = null
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    context,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}