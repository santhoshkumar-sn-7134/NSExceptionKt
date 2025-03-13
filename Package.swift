// swift-tools-version: 5.5
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "NSExceptionKt",
    platforms: [.iOS(.v11), .macOS(.v10_13), .tvOS(.v12), .watchOS(.v7)],
    products: [
        .library(
            name: "NSExceptionKtKSCrash",
            targets: ["NSExceptionKtKSCrash"]
        )
    ],
    dependencies: [
        .package(
            url: "https://github.com/kstenerud/KSCrash.git",
            "1.15.24"..<"2.0.0"
        )
    ],
    targets: [
        .target(
            name: "NSExceptionKtCoreObjC",
            path: "NSExceptionKtCoreObjC",
            publicHeadersPath: "."
        ),
        .target(
            name: "NSExceptionKtKSCrash",
            dependencies: [
                .target(name: "NSExceptionKtCoreObjC"),
                .product(name: "KSCrash", package: "KSCrash")
            ],
            path: "NSExceptionKtKSCrash"
        )
    ],
    swiftLanguageVersions: [.v5]
)
