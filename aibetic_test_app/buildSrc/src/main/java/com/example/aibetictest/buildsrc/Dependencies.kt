@file:Suppress("unused")

package com.example.aibetictest.buildsrc

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.1.2"

    object Kotlin {
        @Suppress("MemberVisibilityCanBePrivate")
        const val version = "1.6.20"
        const val kspVersion = "$version-1.0.5"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2"
        const val dateTime = "org.jetbrains.kotlinx:kotlinx-datetime:0.3.2"
    }

    object Coroutines {
        private const val version = "1.6.1"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.7.0"

        object Compose {
            @Suppress("MemberVisibilityCanBePrivate")
            const val version = "1.2.0-alpha08"

            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val layout = "androidx.compose.foundation:foundation-layout:$version"

            const val activity = "androidx.activity:activity-compose:1.4.0"

            const val navigation = "androidx.navigation:navigation-compose:2.4.1"

            const val ui = "androidx.compose.ui:ui:$version"
            const val material = "androidx.compose.material:material:$version"
            const val materialIconsExtended = "androidx.compose.material:material-icons-extended:$version"
            const val constraintLayout = "androidx.constraintlayout:constraintlayout-compose:1.0.0"

            const val tooling = "androidx.compose.ui:ui-tooling:$version"
            const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:$version"

            const val testManifest = "androidx.compose.ui:ui-test-manifest:$version"
            const val testJunit = "androidx.compose.ui:ui-test-junit4:$version"
        }

        object Lifecycle {
            private const val version = "2.4.1"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }

        object Test {
            private const val version = "1.4.0"
            const val core = "androidx.test:core:$version"
            const val rules = "androidx.test:rules:$version"

            object Ext {
                private const val version = "1.1.3"
                const val junit = "androidx.test.ext:junit-ktx:$version"
            }

            const val espressoCore = "androidx.test.espresso:espresso-core:3.4.0"
        }

        object Room {
            private const val version = "2.4.2"
            const val runtime = "androidx.room:room-runtime:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val compiler = "androidx.room:room-compiler:$version"
        }
    }

    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        const val serializationConverter = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
    }

    object Okhttp {
        private const val version = "4.9.3"
        const val client = "com.squareup.okhttp3:okhttp:$version"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"
    }

    object Di {
        private const val version = "3.2.0-beta-1"
        private const val koinKspVersion = "1.0.0-beta-2"
        const val koinCore = "io.insert-koin:koin-core:$version"
        const val koinAndroid = "io.insert-koin:koin-android:$version"
        const val koinAnnotations = "io.insert-koin:koin-annotations:$koinKspVersion"
        const val koinKspCompiler = "io.insert-koin:koin-ksp-compiler:$koinKspVersion"
        const val koinCompose = "io.insert-koin:koin-androidx-compose:$version"
    }

    object Logger {
        const val logger = "com.jakewharton.timber:timber:5.0.1"
    }

    object Test {
        private const val version = "4.13.2"
        const val junit = "junit:junit:$version"
    }
}