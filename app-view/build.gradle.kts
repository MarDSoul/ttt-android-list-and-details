plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.jetbrains.kotlin.android)
	alias(libs.plugins.ksp)
	alias(libs.plugins.hilt)
	alias(libs.plugins.navigation.saveargs.plugin)
}

android {
	namespace = "ttt.mardsoul.list_and_details_view"
	compileSdk = 34

	defaultConfig {
		applicationId = "ttt.mardsoul.list_and_details_view"
		minSdk = 27
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"
	}

	signingConfigs {
		//Use a bundled debug keystore
		getByName("debug") {
			storeFile = rootProject.file("debug.keystore")
			storePassword = "android"
			keyAlias = "androiddebugkey"
			keyPassword = "android"
		}
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
		create("benchmark") {
			initWith(buildTypes.getByName("release"))
			signingConfig = signingConfigs.getByName("debug")
			matchingFallbacks += listOf("release")
			isDebuggable = false
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	kotlinOptions {
		jvmTarget = "1.8"
	}
	buildFeatures {
		viewBinding = true
	}
}

dependencies {

	implementation(project(":share"))

	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.appcompat)
	implementation(libs.material)
	implementation(libs.androidx.activity)
	implementation(libs.androidx.constraintlayout)

	implementation(libs.hilt.android)
	ksp(libs.hilt.android.compiler)
	implementation(libs.navigation.fragment)
	implementation(libs.navigation.ui)
	implementation(libs.coil)
}
