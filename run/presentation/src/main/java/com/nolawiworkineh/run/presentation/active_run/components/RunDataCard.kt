package com.nolawiworkineh.run.presentation.active_run.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nolawiworkineh.core.presentation.designsystem.PacePalBlack
import com.nolawiworkineh.core.presentation.designsystem.PacePalTheme
import com.nolawiworkineh.presentation.ui.formatted
import com.nolawiworkineh.presentation.ui.toFormattedKm
import com.nolawiworkineh.presentation.ui.toFormattedPace
import com.nolawiworkineh.run.domain.RunData
import com.nolawiworkineh.run.presentation.R
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

@Composable
fun RunDataCard(
    elapsedTime: Duration,  // Total time the user has been running.
    runData: RunData,  // Contains distance, pace, and location details.
    modifier: Modifier = Modifier  //  Allows customizing the layout and appearance.
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))  // **Clip Shape**: Rounds the corners of the card.
            .background(PacePalBlack)  // **Background**: Sets the background color of the card.
            .padding(16.dp),  // **Padding**: Adds space inside the card.
        horizontalAlignment = Alignment.CenterHorizontally  // **Alignment**: Centers the content horizontally.
    ) {
        // **Run Data Item for Elapsed Time**: Displays the elapsed time.
        RunDataItem(
            title = stringResource(id = R.string.duration),  // **Title**: "Duration".
            value = elapsedTime.formatted(),  // **Value**: The formatted elapsed time (e.g., "01:00:05").
            valueFontSize = 32.sp  // **Font Size**: Sets the font size of the value.
        )
        Spacer(modifier = Modifier.height(24.dp))  // **Spacer**: Adds vertical space between items.
        Row(
            modifier = Modifier
                .fillMaxWidth(),  // **Full Width**: Expands the row to fill the card's width.
            verticalAlignment = Alignment.CenterVertically,  // **Align Items Vertically**: Centers the items vertically.
            horizontalArrangement = Arrangement.SpaceAround  // **Space Around**: Distributes space evenly around the items.
        ) {
            // **Run Data Item for Distance**: Displays the distance.
            RunDataItem(
                title = stringResource(id = R.string.distance),  // **Title**: "Distance".
                value = if (runData.distanceMeters == 0) "-" else ((runData.distanceMeters / 1000.0).toFormattedKm()),  // **Value**: Formats the distance in kilometers.
                modifier = Modifier
                    .defaultMinSize(minWidth = 75.dp)  // **Min Width**: Ensures the item has a minimum width.
            )
            // **Run Data Item for Pace**: Displays the pace.
            RunDataItem(
                title = stringResource(id = R.string.pace),  // **Title**: "Pace".
                value = elapsedTime.toFormattedPace(  // **Value**: Formats the pace based on elapsed time and distance.
                    distanceKm = (runData.distanceMeters / 1000.0)  // **Distance in Km**: Converts meters to kilometers.
                ),
                modifier = Modifier
                    .defaultMinSize(minWidth = 75.dp)  // **Min Width**: Ensures the item has a minimum width.
            )
        }
    }
}


@Composable
private fun RunDataItem(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    valueFontSize: TextUnit = 16.sp
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 12.sp
        )
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = valueFontSize
        )
    }
}

@Preview
@Composable
private fun RunDataCardPreview() {
    PacePalTheme {
        RunDataCard(
            elapsedTime = 10.minutes,
            runData = RunData(
                distanceMeters = 45453425,
                pace = 7.minutes
            )
        )
    }
}