package id.capstone.project.skindetector.ui.fragment.other.detectionresult

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.storage.FirebaseStorage
import id.capstone.project.skindetector.data.model.DetectResultEntity
import id.capstone.project.skindetector.domain.usecase.SkinUseCase
import java.io.File

class DetectionResultViewModel(private val useCase: SkinUseCase) : ViewModel() {
    companion object {
        private const val TAG = "DetectionResultViewMode"
    }

    var imagePath: Uri? = null

    fun detectImage(imagePath: String): LiveData<DetectResultEntity> =
        useCase.detectImage(imagePath)

    fun uploadFiles(storage: FirebaseStorage, path: String) {
        // Create a storage reference from our app
        val storageRef = storage.reference

        var file = Uri.fromFile(File(path))
        val riversRef = storageRef.child("images/${file.lastPathSegment}")
        val uploadTask = riversRef.putFile(file)

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
            Log.e(TAG, "uploadFiles: ${it.message}")
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
            Log.d(TAG, "uploadFiles: Success ${taskSnapshot.uploadSessionUri}")
        }
    }
}