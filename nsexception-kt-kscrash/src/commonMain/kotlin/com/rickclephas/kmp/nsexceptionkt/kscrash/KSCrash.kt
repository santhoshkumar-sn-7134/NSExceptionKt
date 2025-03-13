package com.rickclephas.kmp.nsexceptionkt.kscrash

import com.rickclephas.kmp.nsexceptionkt.kscrash.cinterop.*
import com.rickclephas.kmp.nsexceptionkt.core.asNSException
import com.rickclephas.kmp.nsexceptionkt.core.causes
import com.rickclephas.kmp.nsexceptionkt.core.wrapUnhandledExceptionHook
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSException

/**
 * Sets the unhandled exception hook such that all unhandled exceptions are logged to KSCrash as fatal exceptions.
 * If an unhandled exception hook was already set, that hook will be invoked after the exception is logged.
 * Note: once the exception is logged the program will be terminated.
 * @see wrapUnhandledExceptionHook
 */
@OptIn(ExperimentalForeignApi::class)
public fun setKSCrashUnhandledExceptionHook(): Unit = wrapUnhandledExceptionHook { throwable ->
    val exception = throwable.asNSException()
    val causes = throwable.causes.map { it.asNSException() }
    // Notify will persist unhandled events, so we can safely terminate afterwards.
    // https://github.com/kstenerud/KSCrash/blob/master/Source/KSCrash/Recording/Tools/KSLogger.c#L744
    KSCrash.sharedInstance().reportUserException(exception.name, exception.reason, exception.callStackReturnAddresses, causes)
}

/**
 * Converts `this` [NSException] to a [KSCrashReport].
 */
@OptIn(ExperimentalForeignApi::class)
private fun NSException.asKSCrashReport(): KSCrashReport = KSCrashReport().apply {
    errorClass = name
    errorMessage = reason
    stacktrace = KSCrashStackframe.stackframesWithCallStackReturnAddresses(callStackReturnAddresses)
    type = KSCrashErrorType.KSCrashErrorTypeCocoa
}
