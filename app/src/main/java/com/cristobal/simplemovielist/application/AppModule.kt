package com.cristobal.simplemovielist.application

import android.content.Context
import androidx.room.Room
import com.cristobal.simplemovielist.repository.Repository
import com.cristobal.simplemovielist.repository.RetrofitService
import com.cristobal.simplemovielist.repository.room.FilmDao
import com.cristobal.simplemovielist.repository.room.RoomDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// Dependencies for retrofit and room

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


	private val BASEURL = "https://api.themoviedb.org/3/"

	@Singleton
	@Provides
	fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
				level = HttpLoggingInterceptor.Level.BODY
			}

	@Singleton
	@Provides
	fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

	@Singleton
	@Provides
	fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASEURL).client(okHttpClient).build()

	@Singleton
	@Provides
	fun provideApiService(retrofit: Retrofit): RetrofitService = retrofit.create(RetrofitService::class.java)

	@Singleton
	@Provides
	fun providesRepository(retrofitService: RetrofitService, filmDao: FilmDao) = Repository(retrofitService, filmDao)

	@Provides
	@Singleton
	fun provideAppDatabase(@ApplicationContext appContext: Context): RoomDataBase {
		return Room.databaseBuilder(
			appContext,
			RoomDataBase::class.java,
			"filmsdb").build()
	}

	@Provides
	fun providesFilmDao(roomDataBase: RoomDataBase): FilmDao {
		return roomDataBase.filmDao()
	}
}