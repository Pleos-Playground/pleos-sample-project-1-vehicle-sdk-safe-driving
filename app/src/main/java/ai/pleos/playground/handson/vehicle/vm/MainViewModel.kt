package ai.pleos.playground.handson.vehicle.vm

import ai.pleos.playground.handson.vehicle.data.MainUIState
import ai.pleos.playground.handson.vehicle.data.TestStatus
import ai.pleos.playground.handson.vehicle.data.WarningType
import ai.pleos.playground.handson.vehicle.repository.google.CarPropertyRepository
import ai.pleos.playground.handson.vehicle.repository.sdk.VehicleSdkRepository
import ai.pleos.playground.vehicle.listener.SteeringWheelAngleListener
import android.car.VehicleGear
import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager.CarPropertyEventCallback
import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.round

typealias Count = Int

@HiltViewModel
class MainViewModel @Inject constructor(
    private val carPropertyRepository: CarPropertyRepository,
    private val vehicleSdkRepository: VehicleSdkRepository,
) : ViewModel() {
    private val logTag = "[MainViewModel]"
    private val _uiState = MutableStateFlow(
        MainUIState(
            score = 100,
            testStatus = TestStatus.Waiting,
        )
    )
    val uiState = _uiState.asStateFlow()
    private val warningMap: MutableMap<WarningType, Count> = mutableMapOf()
    private val currentSpeed: MutableStateFlow<Float> = MutableStateFlow(0f)
    private val currentSteeringWheelAngle: MutableStateFlow<Float> = MutableStateFlow(0f)

    private val carPropertyEventCallback = object : CarPropertyEventCallback {
        override fun onChangeEvent(value: CarPropertyValue<*>?) {
            value?.let { v ->
                when (v.propertyId) {
                    VehiclePropertyIds.PERF_VEHICLE_SPEED_DISPLAY -> {
                        observeSpeed(v.value as Float)
                    }

                    VehiclePropertyIds.GEAR_SELECTION -> {
                        observeGear(v.value as Int)
                    }

                    else -> { /* TODO */ }
                }
            }
        }

        override fun onErrorEvent(propertyId: Int, areaId: Int) {
            Log.w(logTag, "onErrorEvent: propertyId: $propertyId, areaId: $areaId")
        }
    }

    private val steeringWheelAngleListener = object : SteeringWheelAngleListener {
        override fun onFailed(e: Exception) {
            Log.w(logTag, "SteeringWheel Angle onFailed called.", e)
        }

        override fun onSteeringWheelAngleUpdated(angle: Float?) {
            if (angle != null) {
                observeSteeringWheelAngle(angle)
            }
        }

    }

    fun startDriving() {
        warningMap.clear()
        _uiState.update { it.copy(score = 100, testStatus = TestStatus.Driving(true)) }
    }

    fun startWarning(warningType: WarningType = WarningType.PeelingOut) {
        warningMap[warningType] = (warningMap[warningType] ?: 0) + 1
        _uiState.update {
            it.copy(
                score = if (it.score == 0) {
                    0
                } else {
                    it.score - warningType.score
                },
                testStatus = TestStatus.Warning(
                    warningType,
                    System.currentTimeMillis(),
                    false
                )
            )
        }
    }

    fun updateWarning(warningType: WarningType = WarningType.PeelingOut) {
        warningMap[warningType] = (warningMap[warningType] ?: 0) + 1
        _uiState.update {
            it.copy(
                score = if (it.score == 0) {
                    0
                } else {
                    it.score - warningType.score
                },
                testStatus = TestStatus.Warning(
                    warningType,
                    System.currentTimeMillis(),
                    true
                )
            )
        }
    }

    fun showDriving() {
        _uiState.update { it.copy(testStatus = TestStatus.Driving(false)) }
    }

    fun stopDriving() {
        _uiState.update {
            it.copy(
                testStatus = TestStatus.End
            )
        }
    }

    fun showResult() {
        _uiState.update {
            it.copy(
                testStatus = TestStatus.Result(warningMap.toMap())
            )
        }
    }

    fun subscribeSpeed() {
        carPropertyRepository.registerPropertyEvent(
            propertyId = VehiclePropertyIds.PERF_VEHICLE_SPEED_DISPLAY,
            callback = carPropertyEventCallback
        )
    }

    fun startEvent() {
        carPropertyRepository.registerPropertyEvent(
            propertyId = VehiclePropertyIds.GEAR_SELECTION,
            callback = carPropertyEventCallback
        )
        vehicleSdkRepository.registerSteeringWheelAngle(listener = steeringWheelAngleListener)
    }

    fun stopEvent() {
        carPropertyRepository.unregisterPropertyEvent(
            propertyId = VehiclePropertyIds.GEAR_SELECTION,
            callback = carPropertyEventCallback
        )
        carPropertyRepository.unregisterPropertyEvent(
            propertyId = VehiclePropertyIds.PERF_VEHICLE_SPEED_DISPLAY,
            callback = carPropertyEventCallback
        )
        vehicleSdkRepository.unregisterSteeringWheelAngle(listener = steeringWheelAngleListener)
    }

    // TODO: Vehicle SDK 활용 방법 - Get Steering Wheel Angle API
    fun getSteeringWheelAngle() {
        vehicleSdkRepository.getSteeringWheelAngle(
            onSuccess = { angle ->
                Log.d(logTag, "SteeringWheel Angle is $angle")
            },
            onFailure = { e->
                e.printStackTrace()
            }
        )
    }

    private fun observeGear(newGear: Int) {
        when (uiState.value.testStatus) {
            is TestStatus.Waiting -> {
                if (newGear == VehicleGear.GEAR_DRIVE) {
                    startDriving()
                }
            }

            is TestStatus.Warning,
            is TestStatus.Driving -> {
                if (newGear == VehicleGear.GEAR_PARK) {
                    stopDriving()
                }
            }

            is TestStatus.Result -> {
                if (newGear == VehicleGear.GEAR_DRIVE) {
                    startDriving()
                }
            }

            TestStatus.End -> { /* TODO */ }
        }
    }

    // The unit of speed is meters per second (m/s), so we should convert it to km/h.
    private fun observeSpeed(newSpeed: Float) {
        if (newSpeed == currentSpeed.value) return
        val diffSpeed = round((newSpeed - currentSpeed.value) * 3.6f)
        Log.d("TEST", "diff speed: $diffSpeed km/h.")
        when (uiState.value.testStatus) {
            is TestStatus.Warning -> {
                if (diffSpeed >= 30f) {
                    updateWarning(WarningType.PeelingOut)
                } else if (diffSpeed <= -30f) {
                    updateWarning(WarningType.ScreechingHalt)
                }
            }

            is TestStatus.Driving -> {
                if (diffSpeed >= 30f) {
                    startWarning(WarningType.PeelingOut)
                } else if (diffSpeed <= -30f) {
                    startWarning(WarningType.ScreechingHalt)
                }
            }

            is TestStatus.End -> { /* TODO */}
            is TestStatus.Result -> { /* TODO */ }
            is TestStatus.Waiting -> { /* TODO */ }
        }

        currentSpeed.tryEmit(newSpeed)
    }

    private fun observeSteeringWheelAngle(newAngle: Float) {
        if (currentSteeringWheelAngle.value == newAngle) return
        val diffAngle = round(abs(currentSteeringWheelAngle.value - newAngle))
        Log.d("TEST", "diff angle: $diffAngle")

        // Left is negative.
        // Right is positive.
        when (uiState.value.testStatus) {
            is TestStatus.Warning -> {
                if (diffAngle >= 50f) {
                    updateWarning(WarningType.Incautious)
                }
            }

            is TestStatus.Driving -> {
                if (diffAngle >= 50f) {
                    startWarning(WarningType.Incautious)
                }
            }

            is TestStatus.End -> { /* TODO */ }
            is TestStatus.Result -> { /* TODO */ }
            is TestStatus.Waiting -> { /* TODO */ }
        }
        currentSteeringWheelAngle.tryEmit(newAngle)
    }
}