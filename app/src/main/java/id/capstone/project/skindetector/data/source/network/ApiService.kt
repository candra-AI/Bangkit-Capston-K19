package id.capstone.project.skindetector.data.source.network

import id.capstone.project.skindetector.data.source.network.response.SkinDetectionResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PartMap


interface ApiService {
    @Multipart
    @POST("predict")
    fun detectImage(
        @PartMap map: HashMap<String, RequestBody>?
    ): Call<SkinDetectionResponse>

//    @Multipart
//    @POST("XXXX")
//    fun myPlans(
//        @Part(Constants.ACTION_ID) actionId: RequestBody?,
//        @Part(Constants.OFFER_CODE) offerCode: RequestBody?
//    ): Call<PlanResponse?>?
}