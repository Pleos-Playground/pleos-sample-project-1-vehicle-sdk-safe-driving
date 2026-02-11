package ai.pleos.playground.handson.vehicle.di

import ai.pleos.playground.handson.vehicle.repository.google.CarPropertyRepository
import ai.pleos.playground.handson.vehicle.repository.google.CarPropertyRepositoryImpl
import ai.pleos.playground.handson.vehicle.repository.sdk.VehicleSdkRepository
import ai.pleos.playground.handson.vehicle.repository.sdk.VehicleSdkRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface VehicleBinds {

    @Binds
    fun bindVehicleRepository(impl: VehicleSdkRepositoryImpl): VehicleSdkRepository

    @Binds
    fun bindCarPropertyRepository(impl: CarPropertyRepositoryImpl): CarPropertyRepository
}