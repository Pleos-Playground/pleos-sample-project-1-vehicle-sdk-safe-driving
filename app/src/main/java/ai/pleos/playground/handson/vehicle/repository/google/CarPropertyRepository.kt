package ai.pleos.playground.handson.vehicle.repository.google

import android.car.hardware.property.CarPropertyManager.CarPropertyEventCallback

interface CarPropertyRepository {
    fun registerPropertyEvent(propertyId: Int, callback: CarPropertyEventCallback)
    fun unregisterPropertyEvent(propertyId: Int, callback: CarPropertyEventCallback)
    fun getSpeedDisplay(): Float
    fun getGearSelection(): Int
}