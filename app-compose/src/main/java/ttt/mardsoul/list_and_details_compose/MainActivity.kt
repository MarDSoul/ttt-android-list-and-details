package ttt.mardsoul.list_and_details_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import dagger.hilt.android.AndroidEntryPoint
import ttt.mardsoul.list_and_details_compose.ui.theme.ListAndDetailsTheme

@OptIn(ExperimentalComposeUiApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			val snackbarHostState = remember { SnackbarHostState() }
			ListAndDetailsTheme {
				Scaffold(
					snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
					modifier = Modifier.fillMaxSize()
				) { innerPadding ->
					AppNavigation(
						modifier = Modifier
							.fillMaxSize()
							.padding(innerPadding)
							.semantics { testTagsAsResourceId = true },
						snackbarHostState = snackbarHostState
					)
				}
			}
		}
	}
}