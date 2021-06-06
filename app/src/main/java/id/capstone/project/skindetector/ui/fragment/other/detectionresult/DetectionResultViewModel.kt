package id.capstone.project.skindetector.ui.fragment.other.detectionresult

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.capstone.project.skindetector.data.model.DetectResultEntity
import id.capstone.project.skindetector.domain.usecase.SkinUseCase

class DetectionResultViewModel(private val useCase: SkinUseCase) : ViewModel() {
    var imagePath: Uri? = null

    fun detectImage(imagePath: String): LiveData<DetectResultEntity> =
        useCase.detectImage(imagePath)
}