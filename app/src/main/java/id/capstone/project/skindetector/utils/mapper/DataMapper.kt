package id.capstone.project.skindetector.utils.mapper

import id.capstone.project.skindetector.data.model.DetectResultEntity
import id.capstone.project.skindetector.data.source.network.response.SkinDetectionResponse

object DataMapper {
    fun detectResponseToEntity(response: SkinDetectionResponse): DetectResultEntity =
        with(response) {
            DetectResultEntity(
                label1,
                predict1,
                label2,
                predict2,
                label3,
                predict3
            )
        }
}