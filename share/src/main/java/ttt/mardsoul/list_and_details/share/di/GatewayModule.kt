package ttt.mardsoul.list_and_details.share.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ttt.mardsoul.list_and_details.share.data.GatewayImpl
import ttt.mardsoul.list_and_details.share.domain.Gateway

@Module
@InstallIn(SingletonComponent::class)
abstract class GatewayModule {

	@Binds
	abstract fun bindGateway(gatewayImpl: GatewayImpl): Gateway
}