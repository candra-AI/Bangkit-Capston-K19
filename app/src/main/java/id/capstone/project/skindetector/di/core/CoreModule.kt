package id.capstone.project.skindetector.di.core

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import id.capstone.project.skindetector.data.SkinRepository
import id.capstone.project.skindetector.data.source.network.ApiService
import id.capstone.project.skindetector.data.source.network.RemoteDataSource
import id.capstone.project.skindetector.domain.repository.ISkinRepository
import id.capstone.project.skindetector.utils.helper.ConstantHelper.MAIN_URL
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        ChuckerInterceptor.Builder(androidContext())
            .collector(ChuckerCollector(androidContext()))
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()
    }
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<ChuckerInterceptor>())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(MAIN_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}
val repositoryModule = module {
    single { RemoteDataSource(get()) }
    single<ISkinRepository> { SkinRepository(get()) }
}