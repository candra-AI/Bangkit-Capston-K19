package id.capstone.project.skindetector.domain.usecase

import androidx.lifecycle.LiveData
import id.capstone.project.skindetector.data.model.DetectResultEntity
import id.capstone.project.skindetector.domain.repository.ISkinRepository

class SkinInteractor(private val repository: ISkinRepository) : SkinUseCase {
    override fun detectImage(imagePath: String): LiveData<DetectResultEntity> =
        repository.detectImage(imagePath)

}