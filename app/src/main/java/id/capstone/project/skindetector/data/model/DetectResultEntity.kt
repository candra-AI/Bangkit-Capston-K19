package id.capstone.project.skindetector.data.model

data class DetectResultEntity(
    val firstDisease: String,
    val firstPresentation: Double,
    val secondDisease: String,
    val secondPresentation: Double,
    val thirdDisease: String,
    val thirdPresentation: Double,
)