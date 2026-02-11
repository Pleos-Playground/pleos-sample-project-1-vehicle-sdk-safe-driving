package ai.pleos.playground.handson.vehicle.repository.sdk

import ai.pleos.playground.vehicle.Vehicle
import ai.pleos.playground.vehicle.api.SteeringWheel
import ai.pleos.playground.vehicle.listener.SteeringWheelAngleListener
import javax.inject.Inject

class VehicleSdkRepositoryImpl @Inject constructor(
    private val vehicle: Vehicle
) : VehicleSdkRepository {

    private val steeringWheel: SteeringWheel
        get() = vehicle.getSteeringWheel()

    override fun getSteeringWheelAngle(onSuccess: (Float?) -> Unit, onFailure: (Exception) -> Unit) {
        steeringWheel.getCurrentSteeringWheelAngle(
            onComplete = onSuccess,
            onFailed = onFailure
        )
    }

    override fun registerSteeringWheelAngle(listener: SteeringWheelAngleListener) {
        steeringWheel.registerSteeringWheelAngle(listener)
    }

    override fun unregisterSteeringWheelAngle(listener: SteeringWheelAngleListener) {
        steeringWheel.unregisterSteeringWheelAngle(listener)
    }
}