import NSExceptionKtCoreObjC
import KSCrash

public extension NSExceptionKtReporter where Self == NSExceptionKtReporter {
    /// Configures KSCrash and creates a ``NSExceptionKtCoreObjC/NSExceptionKtReporter`` that will report unhandled Kotlin exceptions to KSCrash.
    /// - Returns: A ``NSExceptionKtCoreObjC/NSExceptionKtReporter`` that will report exceptions to KSCrash.
    static func kscrash() -> NSExceptionKtReporter {
        return KSCrashNSExceptionKtReporter.shared
    }
}

private class KSCrashNSExceptionKtReporter: NSExceptionKtReporter {
    
    static let shared = KSCrashNSExceptionKtReporter()
    
    private init() {}
    
    var requiresMergedException: Bool = false
    
    func reportException(_ exceptions: [NSException]) {
        guard let exception = exceptions.first else { return }
        KSCrash.sharedInstance().reportUserException(exception.name, reason: exception.reason, callStackReturnAddresses: exception.callStackReturnAddresses, causes: exceptions.dropFirst().map { $0 })
    }
}
