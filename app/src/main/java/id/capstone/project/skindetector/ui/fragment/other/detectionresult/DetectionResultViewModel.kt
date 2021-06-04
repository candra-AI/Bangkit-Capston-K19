package id.capstone.project.skindetector.ui.fragment.other.detectionresult

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.capstone.project.skindetector.data.model.DetectResultEntity
import id.capstone.project.skindetector.domain.usecase.SkinUseCase

class DetectionResultViewModel(private val useCase: SkinUseCase) : ViewModel() {
    fun detectImage(imagePath: String): LiveData<DetectResultEntity> =
        useCase.detectImage(imagePath)
}