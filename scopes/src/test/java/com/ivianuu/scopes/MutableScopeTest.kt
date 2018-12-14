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

package com.ivianuu.scopes

import com.ivianuu.scopes.util.TestCloseListener
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MutableScopeTest {

    private val scope = MutableScope()
    private val listener = TestCloseListener()

    @Test
    fun testCloseScope() {
        scope.addListener(listener)
        assertEquals(0, listener.closeCalls)
        assertFalse(scope.isClosed)

        scope.close()
        assertEquals(1, listener.closeCalls)
        assertTrue(scope.isClosed)

        scope.close()
        assertEquals(1, listener.closeCalls)
        assertTrue(scope.isClosed)
    }

    @Test
    fun testNewListenerAfterCloseWillGetNotified() {
        scope.close()
        scope.addListener(listener)
        assertEquals(1, listener.closeCalls)
    }

    @Test
    fun testRemoveListeners() {
        scope.addListener(listener)
        scope.removeListener(listener)

        scope.close()

        assertEquals(0, listener.closeCalls)
    }

    @Test
    fun testAddListenerTwice() {
        scope.addListener(listener)
        scope.addListener(listener)

        scope.close()

        assertEquals(1, listener.closeCalls)
    }
}