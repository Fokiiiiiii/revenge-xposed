package io.github.revenge.xposed.tweaks

import java.io.File
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BundleCacheTest {
    @Test
    fun `only a nonempty regular cache file is usable`() {
        val directory = kotlin.io.path.createTempDirectory("bundle-cache").toFile()
        try {
            val bundle = File(directory, "bundle.js")
            assertFalse(bundle.isUsableBundle())
            bundle.createNewFile()
            assertFalse(bundle.isUsableBundle())
            bundle.writeText("cached bundle")
            assertTrue(bundle.isUsableBundle())
            assertFalse(directory.isUsableBundle())
        } finally {
            directory.deleteRecursively()
        }
    }

    @Test
    fun `automatic update failures only interrupt startup without a cache`() {
        assertFalse(shouldReportUpdateFailure(userInitiated = false, hasCache = true))
        assertTrue(shouldReportUpdateFailure(userInitiated = false, hasCache = false))
        assertTrue(shouldReportUpdateFailure(userInitiated = true, hasCache = true))
        assertTrue(shouldReportUpdateFailure(userInitiated = true, hasCache = false))
    }
}
