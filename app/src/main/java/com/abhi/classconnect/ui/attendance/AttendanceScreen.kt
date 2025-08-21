package com.abhi.classconnect.ui.attendance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abhi.classconnect.data.models.AttendanceEntry
import com.abhi.classconnect.data.models.ScoreEntry
import com.abhi.classconnect.data.local.entities.StudentRecord
import com.abhi.classconnect.data.models.SyncStatus
import com.abhi.classconnect.ui.common.ClassRoomTextField
import com.abhi.classconnect.ui.common.ShowMessage
import com.abhi.classconnect.ui.entities.StudentRecordInfo
import com.abhi.classconnect.utils.NO_STUDENTS_RECORDS
import com.abhi.classconnect.utils.SCORE
import com.abhi.classconnect.utils.STUDENT_NAME
import com.abhi.classconnect.utils.SUBJECT
import com.abhi.classconnect.utils.SYNC
import com.abhi.classconnect.utils.currentTimeInMillis

@Composable
internal fun AttendanceScreen(
    viewModel: AttendanceViewModel
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    var studentName by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var score by remember { mutableStateOf("") }


    var isStudentPresent by remember { mutableStateOf(false) }

    val attendanceList = rememberSaveable { mutableListOf<AttendanceEntry>() }




    Column(
        Modifier
            .fillMaxWidth()
            .padding(all = 10.dp)
    ) {

        ClassRoomTextField(
            value = studentName,
            onValueChange = { studentName = it },
            label = STUDENT_NAME,
            placeHolder = STUDENT_NAME,
            singleLine = true,
        )

        ClassRoomTextField(
            value = subject,
            onValueChange = { subject = it },
            label = SUBJECT,
            placeHolder = SUBJECT,
            singleLine = true,
        )

        ClassRoomTextField(
            value = score,
            onValueChange = { score = it },
            label = SCORE,
            placeHolder = SCORE,
            singleLine = true,
            keyBoardType = KeyboardType.Number
        )

        AttendanceChips { position ->
            isStudentPresent = if (position == 0) true else false
            val attendance =
                AttendanceEntry(date = currentTimeInMillis().toString(), isStudentPresent)
            attendanceList.add(attendance)
        }

        Button(
            onClick = {
                viewModel.saveStudentRecord(
                    getRecording(
                        studentName,
                        subject,
                        score,
                        attendanceList
                    )
                )
                studentName = ""
                subject = ""
                score = ""
            }, Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text("Save Student Attendance")
        }


        when (uiState) {
            UiState.NoRecords -> {
                ShowMessage(message = NO_STUDENTS_RECORDS)
            }

            is UiState.Success -> {
                StudentRecordsList(uiState.records)
            }
        }

    }

}

@Composable
fun StudentRecordsList(records: List<StudentRecordInfo>) {


    LazyColumn {
        items(records) { record ->
            StudentRecordCard(record)
        }
    }

}

@Composable
fun StudentRecordCard(record: StudentRecordInfo) {

    val totalScore = record.scores.sumOf { it.marks }

    with(record) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = name
                )

                Text(
                    text = SCORE.plus(" : ").plus(totalScore)
                )
            }

            Text(
                text = SYNC.plus(" : ").plus(syncStatus.name)
            )

        }
    }

}

@Preview
@Composable
internal fun AttendanceChips(
    modifier: Modifier = Modifier,
    onChipCLicked: (Int) -> Unit = {}
) {
    // local selection states for demo — replace with ViewModel state if needed
    val selectedFirst = remember { mutableStateOf(true) }
    val selectedSecond = remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Absolute.Center
    ) {
        FilterChip(
            selected = selectedFirst.value,
            onClick = {
                selectedFirst.value = !selectedFirst.value
                selectedSecond.value = false
                onChipCLicked(0)
            },
            leadingIcon = {
                if (selectedFirst.value) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "first chip icon")
                }
            },
            label = { Text("Present") }
        )
        Spacer(Modifier.width(12.dp))
        FilterChip(
            selected = selectedSecond.value,
            onClick = {
                selectedSecond.value = !selectedSecond.value
                selectedFirst.value = false
                onChipCLicked(1)
            },
            leadingIcon = {
                if (selectedSecond.value) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "second chip icon")
                }
            },
            label = { Text("Absent") }
        )
    }


}


private fun getRecording(
    studentName: String,
    subject: String,
    score: String,
    attendanceList: MutableList<AttendanceEntry>
): StudentRecord {
    val score = ScoreEntry(subject, score.toInt())
    return StudentRecord(
        name = studentName,
        attendance = attendanceList,
        scores = listOf(score),
        syncStatus = SyncStatus.PENDING
    )
}
