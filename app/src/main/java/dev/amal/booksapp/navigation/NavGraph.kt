package dev.amal.booksapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import dev.amal.booksapp.model.OnBoardingPageItem
import dev.amal.booksapp.view.BookDetailsScreen
import dev.amal.booksapp.view.BookListScreen
import dev.amal.booksapp.view.BookOnboardingScreen
import dev.amal.booksapp.view.BookProfileScreen
import dev.amal.booksapp.view.BookSplashScreen
import dev.amal.booksapp.viewmodel.MainViewModel

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController) }
    val context = LocalContext.current

    NavHost(navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            BookSplashScreen(actions)
        }

        composable(Screen.Onboarding.route) {
            BookOnboardingScreen(
                actions = actions,
                onboardingPages = listOf(
                    OnBoardingPageItem.First,
                    OnBoardingPageItem.Second,
                    OnBoardingPageItem.Third
                )
            )
        }

        composable(Screen.BookList.route) {
            val viewModel: MainViewModel = viewModel(
                factory = HiltViewModelFactory(LocalContext.current, it)
            )
            viewModel.getAllBooks(context = context)
            BookListScreen(viewModel, actions)
        }

        composable(
            "${Screen.Details.route}/{id}",
            arguments = listOf(navArgument(EndPoints.ID) { type = NavType.StringType })
        ) {
            val viewModel = hiltViewModel<MainViewModel>(it)
            val isbnNo = it.arguments?.getString(EndPoints.ID)
                ?: throw IllegalStateException("'Book ISBN No' shouldn't be null")

            viewModel.getBookByID(context = context, isbnNO = isbnNo)
            BookDetailsScreen(viewModel, actions)
        }

        composable(Screen.Profile.route) {
            val viewModel: MainViewModel = viewModel(
                factory = HiltViewModelFactory(LocalContext.current, it)
            )
            viewModel.getProfile(context = context)
            BookProfileScreen(viewModel, actions)
        }
    }
}

class MainActions(navController: NavController) {
    val upPress: () -> Unit = {
        navController.navigateUp()
    }

    val gotoBookDetails: (String) -> Unit = { isbnNo ->
        navController.navigate("${Screen.Details.route}/$isbnNo")
    }

    val gotoBookList: () -> Unit = {
        navController.navigate(Screen.BookList.route)
    }

    val gotoBookOnboarding: () -> Unit = {
        navController.navigate(Screen.Onboarding.route)
    }


    val gotoProfile: () -> Unit = {
        navController.navigate(Screen.Profile.route)
    }
}

object EndPoints {
    const val ID = "id"
}