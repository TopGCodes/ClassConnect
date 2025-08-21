package com.abhi.classconnect.ui.addlesson

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.abhi.classconnect.data.fakeTeachers
import com.abhi.classconnect.data.models.Attachment
import com.abhi.classconnect.data.local.entities.Lesson
import com.abhi.classconnect.data.models.SyncStatus
import com.abhi.classconnect.ui.common.ClassRoomTextField
import com.abhi.classconnect.utils.LESSON_CONTENT
import com.abhi.classconnect.utils.LESSON_TITLE
import com.abhi.classconnect.utils.createImageUri
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddLessonScreen(
    onSaveClick: (Lesson) -> Unit = {},
) {

    val context = LocalContext.current

    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val photoUri = remember { mutableStateOf<Uri?>(null) }
    val attachments = rememberSaveable { mutableListOf<Attachment?>(null) }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            attachments.add(Attachment(uri = photoUri.value.toString()))
        }

    LaunchedEffect(key1 = Unit) {
        cameraPermissionState.launchPermissionRequest()
    }

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        ClassRoomTextField(
            value = title,
            onValueChange = { title = it },
            label = LESSON_TITLE,
            placeHolder = LESSON_TITLE,
            singleLine = true
        )

        ClassRoomTextField(
            value = content,
            onValueChange = { content = it },
            label = LESSON_CONTENT,
            placeHolder = LESSON_CONTENT,
            singleLine = false
        )

        // Row with Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    onSaveClick(getLesson(title, content, attachments))
                }, Modifier.weight(1f)
            ) {
                Text("Save Lesson")
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = {
                    val uri = createImageUri(context)
                    photoUri.value = uri
                    cameraLauncher.launch(uri)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Open Camera")
            }
        }
    }
}

private fun getLesson(
    title: String,
    content: String,
    attachments: List<Attachment?>
): Lesson {
    return Lesson(
        title = title,
        content = content,
        attachments = attachments.filterNotNull(),
        syncStatus = SyncStatus.PENDING,
        modifiedBy = fakeTeachers.random(),
    )
}

