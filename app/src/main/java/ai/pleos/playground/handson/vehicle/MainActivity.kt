package ai.pleos.playground.handson.vehicle

import ai.pleos.playground.handson.vehicle.screen.MainScreen
import ai.pleos.playground.handson.vehicle.ui.theme.VehicleTheme
import ai.pleos.playground.handson.vehicle.vm.MainViewModel
import ai.pleos.playground.vehicle.Vehicle
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


private const val SPEED_PERMISSION_NAME = "android.car.permission.CAR_SPEED"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val logTag = "[MainActivity]"

    @Inject
    lateinit var vehicle: Vehicle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vehicle.initialize()

        enableEdgeToEdge()
        setContent {
            val viewModel: MainViewModel by viewModels()
            // check dangerous permission for CAR_SPEED
            val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                Log.d(logTag, "$SPEED_PERMISSION_NAME: $isGranted")
                if (isGranted) {
                    viewModel.subscribeSpeed()
                }
            }

            LaunchedEffect(launcher) { launcher.launch(SPEED_PERMISSION_NAME) }

            VehicleTheme {
                val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = colorResource(id = R.color.system_surface_basic))
                        .padding(top = 48.dp),
                ) { innerPadding ->
                    Box(
                        Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .background(color = colorResource(id = R.color.system_surface_basic))
                    ) {
                        MainScreen(
                            uiState = uiState,
                            showDriving = viewModel::showDriving,
                            showResult = viewModel::showResult
                        )
                    }
                }
            }

            LifecycleStartEffect(viewModel) {
                viewModel.startEvent()
                onStopOrDispose {
                    viewModel.stopEvent()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        vehicle.release()
    }
}
