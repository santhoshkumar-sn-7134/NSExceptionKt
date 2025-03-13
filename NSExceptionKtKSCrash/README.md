# NSExceptionKt for KSCrash

## Introduction

The `NSExceptionKtKSCrash` module provides integration with KSCrash for reporting unhandled Kotlin exceptions. This module allows you to log unhandled exceptions to KSCrash, ensuring that you have detailed crash reports for your Kotlin applications on Apple platforms.

## Installation

First, make sure you have set up KSCrash in your project. You can add KSCrash to your project using Swift Package Manager by adding the following dependency to your `Package.swift` file:

```swift
.package(url: "https://github.com/kstenerud/KSCrash.git", from: "1.15.24")
```

After that, add and export the Kotlin dependency to your `appleMain` source set.

```kotlin
kotlin {
    iosArm64 { // and/or any other Apple target 
        binaries.framework {
            isStatic = true // it's recommended to use a static framework
            export("com.rickclephas.kmp:nsexception-kt-core:<version>")
        }
    }
    sourceSets {
        appleMain {
            dependencies {
                api("com.rickclephas.kmp:nsexception-kt-core:<version>")
            }
        }
    }
}
```

## Usage

To configure and use the `NSExceptionKtKSCrash` module to report unhandled Kotlin exceptions to KSCrash, follow these steps:

1. Import the necessary modules in your Swift code:

```swift
import KSCrash
import NSExceptionKtKSCrash
import shared // This is your shared Kotlin module
```

2. Update your application delegate to configure KSCrash and add the reporter:

```swift
class AppDelegate: NSObject, UIApplicationDelegate {
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil
    ) -> Bool {
        let kscrash = KSCrash.sharedInstance()
        NSExceptionKt.addReporter(.kscrash())
        return true
    }
}
```

## Example

Here is a complete example demonstrating the configuration and usage of the `NSExceptionKtKSCrash` module:

```swift
import UIKit
import KSCrash
import NSExceptionKtKSCrash
import shared // This is your shared Kotlin module

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil
    ) -> Bool {
        let kscrash = KSCrash.sharedInstance()
        NSExceptionKt.addReporter(.kscrash())
        return true
    }
}
```

## License

This project is licensed under the MIT License. See the [LICENSE](../LICENSE.txt) file for more details.
