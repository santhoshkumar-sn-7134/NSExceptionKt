plugins {
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.kotlin.multiplatform)
    `nsexception-kt-publish`
}

kotlin {
    explicitApi()
    jvmToolchain(11)

    val macosX64 = macosX64()
    val macosArm64 = macosArm64()
    val iosArm64 = iosArm64()
    val iosX64 = iosX64()
    val iosSimulatorArm64 = iosSimulatorArm64()
    val watchosArm32 = watchosArm32()
    val watchosArm64 = watchosArm64()
    val watchosX64 = watchosX64()
    val watchosSimulatorArm64 = watchosSimulatorArm64()
    val watchosDeviceArm64 = watchosDeviceArm64()
    val tvosArm64 = tvosArm64()
    val tvosX64 = tvosX64()
    val tvosSimulatorArm64 = tvosSimulatorArm64()

    sourceSets {
        all {
            languageSettings.optIn("com.rickclephas.kmp.nsexceptionkt.core.InternalNSExceptionKtApi")
        }
        commonMain {
            dependencies {
                implementation(project(":nsexception-kt-core"))
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
    listOf(
        macosX64, macosArm64,
        iosArm64, iosX64, iosSimulatorArm64,
        watchosArm32, watchosArm64, watchosX64, watchosSimulatorArm64, watchosDeviceArm64,
        tvosArm64, tvosX64, tvosSimulatorArm64
    ).forEach {
        it.compilations.getByName("main") {
            cinterops.create("KSCrash") {
                includeDirs("$projectDir/src/nativeInterop/cinterop/KSCrash")
            }
        }
    }
}
