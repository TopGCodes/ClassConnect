package com.abhi.classconnect.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.abhi.classconnect.R
import com.abhi.classconnect.utils.SYNC

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmallTopAppBar(
    onSyncClick : () -> Unit,
    onlineStatus: String,
    syncStatus: String
) {

    Row(
        Modifier
            .background(Color.Black)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,

        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = onlineStatus,
            modifier = Modifier
                .padding(top = 10.dp, start = 5.dp)
                .width(220.dp),
            color = Color.White
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.sync_icon),
                contentDescription = SYNC,
                tint = Color.White,
                modifier = Modifier.clickable{

                }
            )
            Spacer(Modifier.height(1.dp))
            Text(
                text = SYNC,
                color = Color.White,
                modifier = Modifier
            )
        }
        Text(
            ": $syncStatus",
            color = Color.White,
            modifier = Modifier.padding(end = 5.dp)
        )

    }
}