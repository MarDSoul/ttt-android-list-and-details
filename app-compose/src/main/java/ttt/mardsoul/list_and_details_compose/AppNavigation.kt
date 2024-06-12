package ttt.mardsoul.list_and_details_compose

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Composable
fun AppNavigation(
	modifier: Modifier = Modifier,
	snackbarHostState: SnackbarHostState,
	navController: NavHostController = rememberNavController(),
) {
	NavHost(navController = navController, startDestination = ListScreen) {
		composable<ListScreen> {
			ListScreen(modifier = modifier, snackbarHostState = snackbarHostState) {
				navController.navigate(DetailsScreen(it.id, it.login, it.avatarUrl))
			}
		}
		composable<DetailsScreen> {
			val args = it.toRoute<DetailsScreen>()
			DetailsScreen(
				modifier = modifier,
				snackbarHostState = snackbarHostState,
				userId = args.userId,
				userLogin = args.userLogin,
				avatarUrl = args.avatarUrl,
			)
		}
	}
}

@Serializable
object ListScreen

@Serializable
data class DetailsScreen(
	val userId: Int,
	val userLogin: String,
	val avatarUrl: String?
)