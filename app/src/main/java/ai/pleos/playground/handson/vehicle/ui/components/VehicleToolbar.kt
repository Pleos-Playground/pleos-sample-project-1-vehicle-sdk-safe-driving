package ai.pleos.playground.handson.vehicle.ui.components

import ai.pleos.playground.handson.vehicle.R
import ai.pleos.playground.handson.vehicle.ui.preview.VehicleHandsonPreview
import ai.pleos.playground.handson.vehicle.ui.theme.VehicleTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

@Composable
fun VehicleToolbar(modifier: Modifier) {
    Row(
        modifier = modifier
            .padding(horizontal = 40.dp, vertical = 10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_title),
            contentDescription = null,
            modifier = Modifier.clip(RoundedCornerShape(20.dp))
        )

        Text(
            textAlign = TextAlign.Left,
            text = stringResource(id = R.string.app_name),
            fontWeight = FontWeight.W700,
            fontSize = TextUnit(32f, TextUnitType.Sp),
            color = colorResource(id = R.color.system_label_gray_primary),
            modifier = Modifier.padding(horizontal = 24.dp),
        )
    }
}

@VehicleHandsonPreview
@Composable
private fun ToolbarPreview() {
    VehicleTheme {
        VehicleToolbar(Modifier.fillMaxWidth())
    }

}