package id.capstone.project.skindetector.data.source.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.capstone.project.skindetector.data.source.network.response.SkinDetectionResponse
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class RemoteDataSource(private val apiService: ApiService) {
    companion object {
        private const val TAG = "RemoteDataSource"
    }
    fun detectImage(imagePath: String): LiveData<SkinDetectionResponse> {
        val dataResult = MutableLiveData<SkinDetectionResponse>()
        val map = HashMap<String, RequestBody>()
        val file = File(imagePath)

        // Parsing any Media type file
        val requestBody = RequestBody.create(MediaType.parse("*/*"), file)
        map["file\"; filename=\"" + file.name + "\""] = requestBody

        apiService.detectImage(map).enqueue(object : Callback<SkinDetectionResponse> {
            override fun onResponse(
                call: Call<SkinDetectionResponse>,
                response: Response<SkinDetectionResponse>
            ) {
                if (response.isSuccessful) {
                    dataResult.value = response.body()
                } else {
                    Log.e(TAG, "onResponse: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<SkinDetectionResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}", t)
            }
        })

        return dataResult
    }
}