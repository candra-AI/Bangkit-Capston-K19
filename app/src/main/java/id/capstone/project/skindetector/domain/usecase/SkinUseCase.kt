package id.capstone.project.skindetector.domain.usecase

import androidx.lifecycle.LiveData
import id.capstone.project.skindetector.data.model.DetectResultEntity

interface SkinUseCase {
    fun detectImage(imagePath: String): LiveData<DetectResultEntity>
}