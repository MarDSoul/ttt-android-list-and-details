package ttt.mardsoul.list_and_details.benchmark_compose

import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val ITERATIONS = 3
private const val PACKAGE_NAME = "ttt.mardsoul.list_and_details_compose"

@RunWith(AndroidJUnit4::class)
class ComposeBenchmark {
	@get:Rule
	val benchmarkRule = MacrobenchmarkRule()

	@Test
	fun startup() = benchmarkRule.measureRepeated(
		packageName = PACKAGE_NAME,
		metrics = listOf(StartupTimingMetric()),
		iterations = ITERATIONS,
		startupMode = StartupMode.COLD
	) {
		//cold startup
		pressHome()
		startActivityAndWait()
	}

	@Test
	fun scrollReposList() = benchmarkRule.measureRepeated(
		packageName = PACKAGE_NAME,
		metrics = listOf(FrameTimingMetric()),
		iterations = ITERATIONS,
		startupMode = StartupMode.WARM,
		setupBlock = {
			pressHome()
			startActivityAndWait()
		}
	) {
		//waiting for list to be loaded
		val listUsersSelector = By.res("users_list")
		device.wait(Until.hasObject(listUsersSelector), 10_000)
		val listUsers = device.findObject(listUsersSelector)
		listUsers.setGestureMargin(device.displayWidth / 5)

		//scroll down to repos list
		repeat(2) {
			listUsers.scroll(Direction.DOWN, 25f)
			device.waitForIdle()
		}
	}

	@Test
	fun clickOnExpandedButton() = benchmarkRule.measureRepeated(
		packageName = PACKAGE_NAME,
		metrics = listOf(FrameTimingMetric()),
		iterations = ITERATIONS,
		startupMode = StartupMode.WARM,
		setupBlock = {
			pressHome()
			startActivityAndWait()
			loadGithubUsers()
		}
	) {
		//click on expanded button
		val userItemSelector = By.res("expanded_button_1")
		device.findObject(userItemSelector).click()
		val repoItemSelector = By.res("repo_item")
		device.wait(Until.hasObject(repoItemSelector), 10_000)
		device.findObject(userItemSelector).click()
		device.waitForIdle()
	}


	@Test
	fun clickToDetails() = benchmarkRule.measureRepeated(
		packageName = PACKAGE_NAME,
		metrics = listOf(FrameTimingMetric()),
		iterations = ITERATIONS,
		startupMode = StartupMode.WARM,
		setupBlock = {
			pressHome()
			startActivityAndWait()
			loadGithubUsers()
		}
	) {
		//click on user
		val userItemSelector = By.res("user_item_2")
		device.findObject(userItemSelector).click()
		device.waitForIdle()

		//waiting for details to be loaded
		val listReposSelector = By.res("repos_list")
		device.wait(Until.hasObject(listReposSelector), 10_000)
		val listRepos = device.findObject(listReposSelector)
		listRepos.setGestureMargin(device.displayWidth / 5)

		//scroll down to repos list
		listRepos.scrollUntil(Direction.DOWN, Until.scrollFinished(Direction.DOWN))
	}
}

internal fun MacrobenchmarkScope.loadGithubUsers() {
	val listUsersSelector = By.res("users_list")
	device.wait(Until.hasObject(listUsersSelector), 10_000)
	val listUsers = device.findObject(listUsersSelector)
	listUsers.setGestureMargin(device.displayWidth / 5)
}