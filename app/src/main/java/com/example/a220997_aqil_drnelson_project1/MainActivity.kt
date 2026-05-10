package com.example.a220997_aqil_drnelson_project1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.a220997_aqil_drnelson_project1.ui.theme.A220997_Aqil_DrNelson_Project1Theme

enum class safeRoadScreen(@StringRes val title: Int){
    Start(title = R.string.Home),
    Report(title = R.string.Report),
    AddReport(title = R.string.AddReport),
    Summary(title = R.string.Summary),
    Login(title = R.string.Login)

}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            A220997_Aqil_DrNelson_Project1Theme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    safeRoadApp()
                }
            }
        }
    }
}

@Composable
fun safeRoadApp(
    reportModel: ReportViewModel = viewModel(),
    userModel: UserViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
){
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = safeRoadScreen.valueOf(
        backStackEntry?.destination?.route ?: safeRoadScreen.Start.name
    )
    val userState by userModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            Navbar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                userState = userState,
                toLogin = { navController.navigate(safeRoadScreen.Login.name) }
            )
        },
        bottomBar = {
            if (stringResource(currentScreen.title) != "Login")
                NaviBar(
                    currentScreen = currentScreen,
                    onHome= {navController.navigate(safeRoadScreen.Start.name)},
                    onMyReport= {navController.navigate(safeRoadScreen.Report.name)}
                )
        },

        // 1. ADD THE BIG CIRCLE BUTTON HERE
        floatingActionButton = {
            if (stringResource(currentScreen.title) == "Home" || stringResource(currentScreen.title) == "My Report"){
                androidx.compose.material3.FloatingActionButton(
                    onClick = { if (userState.username == "Guest") { navController.navigate(safeRoadScreen.Login.name) } else { navController.navigate(safeRoadScreen.AddReport.name) }},
                    shape = androidx.compose.foundation.shape.CircleShape,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    // REMOVE THE SHADOW HERE
                    elevation = androidx.compose.material3.FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp,
                        focusedElevation = 0.dp,
                        hoveredElevation = 0.dp
                    ),
                    modifier = Modifier
                        .size(70.dp)
                        .offset(y = 65.dp)
                        .border(
                            width = 6.dp,
                            color = MaterialTheme.colorScheme.surfaceContainer,
                            shape = androidx.compose.foundation.shape.CircleShape
                        )
                ) {
                    Icon(
                        painter = androidx.compose.ui.res.painterResource(id = R.drawable.add),
                        contentDescription = "Add Report",
                        modifier = Modifier.size(35.dp)
                    )
                }
            }
        },
        // 2. POSITION IT IN THE CENTER
        floatingActionButtonPosition = FabPosition.Center,
    ) { innerPadding ->
        val userState by userModel.uiState.collectAsState()
        val reportState by reportModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = safeRoadScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = safeRoadScreen.Start.name) {
                HomeScreen(
                )
            }

            composable(route = safeRoadScreen.AddReport.name) {
                ReportForm(
                    onSubmitButtonClicked = { sub, cat, desc, loc ->
                        reportModel.setData(sub, cat, desc, loc)
                        navController.navigate(safeRoadScreen.Summary.name)
                    },
                    onCancelButtonClicked = {
                        cancelReportAndNavigateToStart(reportModel, navController)
                    }
                )
            }

            composable(route = safeRoadScreen.Login.name) {
                LoginScreen(
                    onLoginClick = {
                        name -> userModel.setUsername(name)
                        navController.navigate(safeRoadScreen.Start.name)
                    }
                )
            }

            composable(route = safeRoadScreen.Summary.name) {
                SummaryScreen(
                    uiState = reportState,
                    onSendClick = {
                        navController.navigate(safeRoadScreen.Report.name)
                        userModel.addCount()
                    }
                )
            }

            composable(route = safeRoadScreen.Report.name) {
                MyReportScreen(
                    ReportState = reportState,
                    UserState = userState
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navbar(
    userState: UserUiState,
    currentScreen: safeRoadScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    toLogin: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        title = {
            if (stringResource(currentScreen.title) == "Home") {
                Column(
                    modifier = Modifier.padding(vertical = 15.dp)
                ) {
                    Text(
                        text = "SafeRoad",
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Text(
                        text = "Hello ${userState.username}!",
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }else {
                Text(stringResource(currentScreen.title))
            }

        },
        navigationIcon = {
            if (canNavigateBack && stringResource(currentScreen.title) != "Home" && stringResource(currentScreen.title) != "My Report") {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        },
        actions = {
            if (stringResource(currentScreen.title) == "Home" && userState.username == "Guest" || stringResource(currentScreen.title) == "My Report" && userState.username == "Guest") {
                // "Actions" are the items on the right side of the bar
                TextButton(onClick =  toLogin ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Login Icon",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(25.dp)
                        )
                        Spacer(
                            modifier = Modifier
                                .width(6.dp)
                        )
                        Text(
                            text = "Login",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun NaviBar(
    currentScreen: safeRoadScreen,
    onHome: () -> Unit = {},
    onMyReport: () -> Unit = {}
){
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        modifier = Modifier.height(100.dp)
    ) {
        // 1. HOME BUTTON
        NavigationBarItem(
            selected = currentScreen == safeRoadScreen.Start,
            onClick =  onHome ,
            label = { Text("Home") },
            icon = {
                Icon(
                    imageVector = if (currentScreen == safeRoadScreen.Start) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = "Home"
                )
            }
        )

        // 2. EMPTY SPACER ITEM (This creates room for the Big Plus Circle)
        // We use a disabled NavigationBarItem or a simple Spacer
//        NavigationBarItem(
//            selected = false,
//            onClick = { },
//            label = { Text("") },
//            enabled = false,
//            icon = { /* Leave empty to give space to the FAB */ }
//        )

        // 3. ADUAN BUTTON (Replacing Profile)
        NavigationBarItem(
            selected = currentScreen == safeRoadScreen.Report,
            onClick = (onMyReport),
            label = { Text("Report") },
            icon = {
                Icon(
                    // CHANGE THIS: replace 'info_icon' with your actual file name in res/drawable
                    painter = androidx.compose.ui.res.painterResource(id = R.drawable.info),
                    contentDescription = "Aduan",
                    modifier = Modifier.size(24.dp) // Standard icon size
                )
            }
        )
    }
}

private fun cancelReportAndNavigateToStart(
    reportModel: ReportViewModel,
    navController: NavHostController
) {
    reportModel.resetReport()
    navController.popBackStack(safeRoadScreen.Start.name, inclusive = false)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    A220997_Aqil_DrNelson_Project1Theme {

    }
}