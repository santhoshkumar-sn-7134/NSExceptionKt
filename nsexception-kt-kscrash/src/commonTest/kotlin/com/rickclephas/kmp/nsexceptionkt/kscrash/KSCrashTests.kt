package com.rickclephas.kmp.nsexceptionkt.kscrash

import com.rickclephas.kmp.nsexceptionkt.core.asNSException
import com.rickclephas.kmp.nsexceptionkt.core.causes
import com.rickclephas.kmp.nsexceptionkt.core.wrapUnhandledExceptionHook
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class KSCrashTests {

    @OptIn(ExperimentalForeignApi::class)
    @Test
    fun testSetKSCrashUnhandledExceptionHook() {
        var hookCalled = false
        wrapUnhandledExceptionHook { throwable ->
            hookCalled = true
            val exception = throwable.asNSException()
            val causes = throwable.causes.map { it.asNSException() }
            assertNotNull(exception)
            assertTrue(causes.isNotEmpty())
        }
        setKSCrashUnhandledExceptionHook()
        throw RuntimeException("Test exception")
    }

    @OptIn(ExperimentalForeignApi::class)
    @Test
    fun testAsKSCrashReport() {
        val exception = NSException("TestException", "Test reason", null)
        val report = exception.asKSCrashReport()
        assertEquals("TestException", report.errorClass)
        assertEquals("Test reason", report.errorMessage)
        assertNotNull(report.stacktrace)
        assertEquals(KSCrashErrorType.KSCrashErrorTypeCocoa, report.type)
    }
}
