package ttt.mardsoul.list_and_details.share.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter
import retrofit2.Retrofit
import ttt.mardsoul.list_and_details.share.data.GitHubApi
import javax.inject.Singleton

private const val BASE_URL = "https://api.github.com/"

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

	@Provides
	fun provideConverterFactory(): Converter.Factory {
		val json = Json { ignoreUnknownKeys = true }
		return json.asConverterFactory("application/json".toMediaType())
	}

	@Provides
	@Singleton
	fun provideRetrofit(converterFactory: Converter.Factory): Retrofit {
		val json = Json { ignoreUnknownKeys = true }
		return Retrofit.Builder()
			.addConverterFactory(converterFactory)
			.baseUrl(BASE_URL)
			.build()
	}

	@Provides
	@Singleton
	fun providePokemonApi(retrofit: Retrofit): GitHubApi {
		return retrofit.create(GitHubApi::class.java)
	}
}