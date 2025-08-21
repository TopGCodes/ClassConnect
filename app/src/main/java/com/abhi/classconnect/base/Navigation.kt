package com.abhi.classconnect.base

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.abhi.classconnect.ui.addlesson.AddLessonScreen
import com.abhi.classconnect.ui.addlesson.AddLessonViewModel
import com.abhi.classconnect.ui.attendance.AttendanceScreen
import com.abhi.classconnect.ui.attendance.AttendanceViewModel
import com.abhi.classconnect.ui.common.FloatingButton
import com.abhi.classconnect.ui.lessons.LessonsScreen
import com.abhi.classconnect.ui.lessons.LessonsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Navigation() {

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route



    Scaffold(
        modifier = Modifier.wrapContentHeight(),
        bottomBar = {
            BottomNavigation(navController = navController)
        },
        floatingActionButton = {
            if (currentDestination == Destination.Home::class.qualifiedName) {
                FloatingButton {
                    navController.navigate(Destination.AddLesson)
                }
            }
        }

    ) { innerpadding ->

        NavHost(
            navController = navController, startDestination = Destination.Home,
        ){

            composable<Destination.Home> {
                val lessonsVm : LessonsViewModel = koinViewModel()
                LessonsScreen(lessonsVm, navController)
            }

            composable<Destination.Attendance> {
                    val viewModel : AttendanceViewModel = koinViewModel()
                AttendanceScreen(
                    viewModel = viewModel
                )
            }

            composable<Destination.AddLesson> {
                val viewModeL : AddLessonViewModel = koinViewModel()
                AddLessonScreen { lesson ->
                    viewModeL.saveLesson(lesson)
                    navController.popBackStack()
                }
            }
        }
    }
}

