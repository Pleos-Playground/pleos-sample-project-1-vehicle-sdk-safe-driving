package ai.pleos.playground.handson.vehicle.repository.google

import android.car.VehiclePropertyIds
import android.car.hardware.property.CarPropertyManager
import android.car.hardware.property.CarPropertyManager.CarPropertyEventCallback
import javax.inject.Inject

class CarPropertyRepositoryImpl @Inject constructor(
    private val carPropertyManager: CarPropertyManager
) : CarPropertyRepository {
    private companion object {
        const val GLOBAL_AREA_ID = 0
    }

    override fun registerPropertyEvent(propertyId: Int, callback: CarPropertyEventCallback) {
        carPropertyManager.registerCallback(
            callback,
            propertyId,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        )
    }

    override fun unregisterPropertyEvent(propertyId: Int, callback: CarPropertyEventCallback) {
        carPropertyManager.unregisterCallback(callback, propertyId)
    }


    override fun getSpeedDisplay(): Float {
        return carPropertyManager.getFloatProperty(VehiclePropertyIds.PERF_VEHICLE_SPEED_DISPLAY, GLOBAL_AREA_ID)
    }

    override fun getGearSelection(): Int {
        return carPropertyManager.getIntProperty(VehiclePropertyIds.GEAR_SELECTION, GLOBAL_AREA_ID)
    }

}