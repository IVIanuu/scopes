package com.ivianuu.scopes.android

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test

class LifecycleScopesTest {

    private class TestLifecycleOwner : LifecycleOwner {
        val registry = LifecycleRegistry(this)
        override fun getLifecycle(): Lifecycle = registry
    }

    @Test
    fun testLifecycleScopes() {
        val lifecycleOwner = TestLifecycleOwner()

        var closed = false

        lifecycleOwner.onCreate.onClose { closed = true }

        assertFalse(closed)
        lifecycleOwner.registry.currentState = Lifecycle.State.CREATED
        assertTrue(closed)
    }

}