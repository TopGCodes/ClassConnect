package com.abhi.classconnect.ui.lessons

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.abhi.classconnect.base.Destination
import com.abhi.classconnect.ui.SmallTopAppBar
import com.abhi.classconnect.ui.common.FloatingButton
import com.abhi.classconnect.ui.common.ShowMessage
import com.abhi.classconnect.ui.entities.LessonInfo
import com.abhi.classconnect.utils.SYNC
import com.abhi.classconnect.utils.extensions.toDisplayText

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LessonsScreen(
    viewModel: LessonsViewModel,
    navController: NavController
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    when (uiState) {
        is UiState.NoLessons -> {
            ShowMessage()
        }

        is UiState.Success -> {
            Scaffold(
                floatingActionButton = {
                    FloatingButton {
                        navController.navigate(Destination.AddLesson)
                    }
                },
                contentWindowInsets = WindowInsets(bottom = 15.dp),
                floatingActionButtonPosition = FabPosition.EndOverlay,
                topBar = {
                    SmallTopAppBar(
                        onSyncClick = { viewModel.initiateSync() },
                        onlineStatus = uiState.state.isOnline,
                        syncStatus = uiState.state.syncInfo
                    )
                }
            ) { innerpadding ->
                LessonsScreenContent(
                    uiState.state.lessons,
                    innerpadding
                )
            }
        }
    }


}

@Composable
internal fun LessonsScreenContent(lessons: List<LessonInfo>, innerpadding: PaddingValues) {

    LazyColumn(Modifier.padding(innerpadding)) {
        items(lessons, key = { it.lastModified }) {
            LessonCard(lesson = it)
        }
    }
}

@Composable
internal fun LessonCard(
    lesson: LessonInfo
) {
    Card(
        modifier = Modifier
            .padding(all = 15.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(corner = CornerSize(7.dp)))
            .fillMaxWidth()
            .background(Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), // shadow depth
        shape = RoundedCornerShape(16.dp), // rounded corners
        colors = CardDefaults.cardColors(
            containerColor = Color.White // background color
        )
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(all = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = lesson.title.toDisplayText(),
                    fontFamily = FontFamily.Monospace,
                )
                Text(
                    text = "12-23-2000",
                    fontFamily = FontFamily.SansSerif,
                )
            }

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = lesson.content,
                fontFamily = FontFamily.Serif,
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = SYNC.plus(" : ").plus(lesson.syncStatus),
                    fontFamily = FontFamily.SansSerif,
                )
                Text(
                    text = lesson.modifiedBy,
                    fontFamily = FontFamily.SansSerif,
                )
            }





        }


    }

}