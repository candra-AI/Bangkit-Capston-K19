package id.capstone.project.skindetector.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import id.capstone.project.skindetector.data.model.DetectResultEntity
import id.capstone.project.skindetector.data.source.network.RemoteDataSource
import id.capstone.project.skindetector.domain.repository.ISkinRepository
import id.capstone.project.skindetector.utils.mapper.DataMapper.detectResponseToEntity

class SkinRepository(private val remoteDataSource: RemoteDataSource): ISkinRepository {
    override fun detectImage(imagePath: String): LiveData<DetectResultEntity> =
        Transformations.map(remoteDataSource.detectImage(imagePath)) {
            detectResponseToEntity(it)
        }
}