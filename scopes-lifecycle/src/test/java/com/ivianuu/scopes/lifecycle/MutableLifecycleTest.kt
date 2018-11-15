/*
 * Copyright 2018 Manuel Wrage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ivianuu.scopes.lifecycle

import com.ivianuu.scopes.lifecycle.util.TestLifecycle
import com.ivianuu.scopes.lifecycle.util.TestLifecycleListener
import org.junit.Assert.assertEquals
import org.junit.Test

class MutableLifecycleTest {

    private val lifecycle = MutableLifecycle<TestLifecycle>()

    @Test
    fun testNotifyingListeners() {
        val listener = TestLifecycleListener<TestLifecycle>()
        val expectedHistory = mutableListOf<TestLifecycle>()

        lifecycle.addListener(listener)
        assertEquals(expectedHistory, listener.history)

        lifecycle.onEvent(TestLifecycle.CREATE)
        expectedHistory.add(TestLifecycle.CREATE)
        assertEquals(expectedHistory, listener.history)

        lifecycle.onEvent(TestLifecycle.SHOW)
        expectedHistory.add(TestLifecycle.SHOW)
        assertEquals(expectedHistory, listener.history)

        lifecycle.onEvent(TestLifecycle.HIDE)
        expectedHistory.add(TestLifecycle.HIDE)
        assertEquals(expectedHistory, listener.history)

        lifecycle.onEvent(TestLifecycle.DESTROY)
        expectedHistory.add(TestLifecycle.DESTROY)
        assertEquals(expectedHistory, listener.history)
    }

    @Test
    fun testRemoveListeners() {
        val listener = TestLifecycleListener<TestLifecycle>()
        lifecycle.addListener(listener)
        lifecycle.removeListener(listener)

        lifecycle.onEvent(TestLifecycle.CREATE)
        assertEquals(emptyList<TestLifecycle>(), listener.history)
    }

    @Test
    fun testAddListenerTwice() {
        val listener = TestLifecycleListener<TestLifecycle>()
        lifecycle.addListener(listener)
        lifecycle.addListener(listener)

        lifecycle.onEvent(TestLifecycle.CREATE)
        assertEquals(listOf(TestLifecycle.CREATE), listener.history)
    }
}