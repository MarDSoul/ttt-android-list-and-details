package ttt.mardsoul.list_and_details.share.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScopeModule {

	@ApplicationScope
	@Provides
	@Singleton
	fun provideApplicationScope(@DefaultDispatcher defaultDispatcher: CoroutineDispatcher): CoroutineScope {
		return CoroutineScope(SupervisorJob() + defaultDispatcher)
	}

}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope