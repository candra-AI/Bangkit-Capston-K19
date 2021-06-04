package id.capstone.project.skindetector.domain.repository

import androidx.lifecycle.LiveData
import id.capstone.project.skindetector.data.model.DetectResultEntity

interface ISkinRepository {
    fun detectImage(imagePath: String): LiveData<DetectResultEntity>
}