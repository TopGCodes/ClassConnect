package com.abhi.classconnect.base

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.abhi.classconnect.R
import kotlinx.serialization.Serializable


const val HOME = "home"

const val ATTENDANCE = "Attendance"

const val ADD_LESSON = "Add lesson"

@Serializable
sealed class Destination(
    val name: String,
    val icon: Int
) {
    @Serializable
    data object Home : Destination(name = HOME, icon = R.drawable.home_icon)
    @Serializable
    data object Attendance : Destination(name = ATTENDANCE, icon = R.drawable.check_in_out_icon)

    @Serializable
    data object AddLesson : Destination(name = ADD_LESSON, icon = R.drawable.check_in_out_icon)


}


val bottomNavRoutes = listOf(
    Destination.Home,
    Destination.Attendance,
)


@Composable
internal fun BottomNavigation(navController: NavController) {

    var selectedIndex by remember { mutableIntStateOf(0) }

    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
        bottomNavRoutes.forEachIndexed { index, destination ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {
                    navController.navigate(route = destination)
                    selectedIndex = index
                },
                icon = {
                    Icon(
                        painter = painterResource(destination.icon),
                        contentDescription = destination.name
                    )
                },
                label = { Text(destination.name) }
            )
        }
    }
}

