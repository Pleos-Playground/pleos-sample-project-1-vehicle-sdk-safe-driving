package ai.pleos.playground.handson.vehicle.repository.sdk

import ai.pleos.playground.vehicle.listener.SteeringWheelAngleListener

interface VehicleSdkRepository {
    fun getSteeringWheelAngle(onSuccess: (Float?) -> Unit, onFailure: (Exception) -> Unit)
    fun unregisterSteeringWheelAngle(listener: SteeringWheelAngleListener)
    fun registerSteeringWheelAngle(listener: SteeringWheelAngleListener)
}