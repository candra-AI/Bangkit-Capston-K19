package id.capstone.project.skindetector.data.source.network.response

import com.google.gson.annotations.SerializedName

data class SkinDetectionResponse(

	@field:SerializedName("predict2")
	val predict2: Double,

	@field:SerializedName("label1")
	val label1: String,

	@field:SerializedName("predict1")
	val predict1: Double,

	@field:SerializedName("label2")
	val label2: String,

	@field:SerializedName("label3")
	val label3: String,

	@field:SerializedName("predict3")
	val predict3: Double
)
