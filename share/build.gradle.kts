plugins {
	alias(libs.plugins.android.library)
	alias(libs.plugins.jetbrains.kotlin.android)
	alias(libs.plugins.ksp)
	alias(libs.plugins.hilt)
	alias(libs.plugins.serialization)
}

android {
	namespace = "ttt.mardsoul.list_and_details.share"
	compileSdk = 34

	defaultConfig {
		minSdk = 27

		consumerProguardFiles("consumer-rules.pro")
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
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	kotlinOptions {
		jvmTarget = "1.8"
	}
}

dependencies {

	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.appcompat)
	implementation(libs.material)
	implementation(libs.androidx.lifecycle.runtime.ktx)

	implementation(libs.hilt.android)
	ksp(libs.hilt.android.compiler)
	implementation(libs.retrofit)
	implementation(libs.serialization.jetbrains)
	implementation(libs.serialization.jakewharton)
	implementation(libs.okhttp)
}