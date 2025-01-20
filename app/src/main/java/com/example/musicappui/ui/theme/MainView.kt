package com.example.musicappui.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.musicappui.MainViewModel
import com.example.musicappui.Screen
import com.example.musicappui.screensInDrawer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.example.musicappui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView() {
    val scaffoldState : ScaffoldState = rememberScaffoldState()
    val scope : CoroutineScope = rememberCoroutineScope()
    val controller : NavController = rememberNavController()
    val navBackStateEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStateEntry?.destination?.route
    val viewModel : MainViewModel = viewModel()
    val currentScreen = remember {
        viewModel.currentScreen.value
    }
    val title = remember {
        mutableStateOf(currentScreen.title)
    }
    val dialogOpen = remember{
        mutableStateOf(false)
    }


    Scaffold(
        topBar = {
            TopAppBar(title = {Text("Home")},
                navigationIcon = { IconButton(
                    onClick = {
                        // Open drawer
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    },
                   content = { Icon(imageVector = Icons.Default.AccountCircle,
                       contentDescription = "Menu")}
                )  },
                colors = topAppBarColors(
                    containerColor = colorResource(id = R.color.app_bar_color),
                    titleContentColor = colorResource(id = R.color.white),
                ),
            )
        },
        scaffoldState = scaffoldState,
        drawerContent = {
            LazyColumn(Modifier.padding(16.dp)) {
                items(screensInDrawer) {
                    item ->  DrawerItem(selected = currentRoute == item.dRoute, item = item) {
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                    if(item.dRoute == "add_account"){
                        dialogOpen.value= true
                    }else{
                        controller.navigate(item.dRoute)
                        title.value = item.dTitle
                    }
                }
                        controller.navigate(item.dRoute)
                        title.value = item.dTitle

                }
            }
        }
    ) {
        Navigation(controller, viewModel, pd = it)
        AccountDialog(dialogOpen = dialogOpen)
    }
}

@Composable
fun DrawerItem(
    selected : Boolean,
    item : Screen.DrawerScreen,
    onDrawerItemClicked : () -> Unit
) {
    val background = if(selected) Color.DarkGray else Color.White
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 16.dp).
        background(background).clickable {
            onDrawerItemClicked()
        }
    ) {
        Icon(painter = painterResource(id = item.icon), contentDescription = item.dTitle,
            Modifier.padding(end = 8.dp, top = 4.dp)
            )
        Text(text = item.dTitle, style = MaterialTheme.typography.headlineSmall)
    }
}

@Composable
fun Navigation(navController: NavController, viewModel: MainViewModel, pd : PaddingValues) {
    NavHost(navController = navController as NavHostController,
        startDestination = Screen.DrawerScreen.Account.route, modifier = Modifier.padding(pd) ){

        composable(Screen.DrawerScreen.Account.route){
            AccountView()
        }
        composable(Screen.DrawerScreen.Subscription.route){
            //Subscription()
        }
    }
}