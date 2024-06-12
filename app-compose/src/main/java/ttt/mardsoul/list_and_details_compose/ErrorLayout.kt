package ttt.mardsoul.list_and_details_compose

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ttt.mardsoul.list_and_details.share.R
import ttt.mardsoul.list_and_details.share.ui.TYPE_ERROR_MESSAGE

@Composable
fun ErrorLayout(
	modifier: Modifier = Modifier,
	snackbarHostState: SnackbarHostState,
	snackbarMessage: String = TYPE_ERROR_MESSAGE,
	performedAction: () -> Unit = {}
) {
	val snackbarLabel = stringResource(R.string.snackbar_action_label)

	Box(modifier = modifier) {
		LaunchedEffect(true) {
			val result = snackbarHostState.showSnackbar(
				snackbarMessage,
				actionLabel = snackbarLabel,
				duration = SnackbarDuration.Indefinite,
			)
			when (result) {
				SnackbarResult.ActionPerformed -> {
					performedAction()
				}

				SnackbarResult.Dismissed -> {
					// Do nothing
				}
			}
		}
	}
}