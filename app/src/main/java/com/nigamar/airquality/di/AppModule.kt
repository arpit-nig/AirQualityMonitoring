package com.nigamar.airquality.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.nigamar.airquality.BuildConfig
import com.nigamar.airquality.live_monitoring.data.local.CityDao
import com.nigamar.airquality.live_monitoring.data.local.CityDb
import com.nigamar.airquality.live_monitoring.data.repository.CityRepoImpl
import com.nigamar.airquality.live_monitoring.domain.repository.CityRepo
import com.nigamar.airquality.live_monitoring.domain.use_case.GetLiveData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesCityRepo(cityDao: CityDao) : CityRepo {
        return CityRepoImpl(cityDao)
    }

    @Provides
    @Singleton
    fun provideCityDatabase(@ApplicationContext app : Context) : CityDb {
        return Room.databaseBuilder(
            app ,
            CityDb::class.java,
            "cities_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCityDao( cityDb: CityDb ): CityDao {
        return cityDb.getCityDao()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideSocketRequest() : Request {
        return Request.Builder().url(BuildConfig.API_URL).build()
    }

    @Provides
    @Singleton
    fun provideGson() : Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideGetLiveData(cityRepo: CityRepo) : GetLiveData {
        return GetLiveData(cityRepo)
    }

}