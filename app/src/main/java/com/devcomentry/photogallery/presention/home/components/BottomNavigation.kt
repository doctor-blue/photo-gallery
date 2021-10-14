package com.devcomentry.photogallery.presention.home.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.presention.navigation.Screen

@Composable
fun BottomNavigationBar(
    currentScreen: MutableState<Screen>,
    onScreenSelected:(Screen)->Unit
) {
    val items = listOf(
        NavigationItem.AllFile,
        NavigationItem.AlbumsScreen,
    )

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.white),
        contentColor = Color.White
    ) {
        items.forEach { item ->
//            val navBackStackEntry by navController.currentBackStackEntryAsState()
//            val currentRoute = navBackStackEntry?.destination?.route
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = stringResource(item.title)
                    )
                },
                label = { Text(text = stringResource(item.title)) },
                selectedContentColor = colorResource(R.color.purple_500),
                unselectedContentColor = colorResource(R.color.purple_500).copy(0.4f),
                alwaysShowLabel = true,
                selected = item.screen == currentScreen.value,
                onClick = {
                   onScreenSelected(item.screen)
                }
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun BottomNavigationBarPreview() {
//    BottomNavigationBar()
//}