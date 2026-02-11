package ai.pleos.playground.handson.vehicle.ui.components

import ai.pleos.playground.handson.vehicle.R
import ai.pleos.playground.handson.vehicle.ui.preview.VehicleHandsonPreview
import ai.pleos.playground.handson.vehicle.ui.theme.VehicleTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

@Composable
fun Score(score: Int, modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            textAlign = TextAlign.Left,
            text = score.toString(),
            fontWeight = FontWeight.W800,
            fontSize = TextUnit(100f, TextUnitType.Sp),
            color = colorResource(id = R.color.system_label_gray_primary),
        )

        Text(
            textAlign = TextAlign.Left,
            text = stringResource(id = R.string.score),
            fontWeight = FontWeight.W700,
            fontSize = TextUnit(62f, TextUnitType.Sp),
            color = colorResource(id = R.color.system_label_gray_primary),
            modifier = Modifier.padding(start = 16.dp),
        )
    }
}

@VehicleHandsonPreview
@Composable
private fun ScorePreview() {
    VehicleTheme {
        Score(100, Modifier)
    }
}