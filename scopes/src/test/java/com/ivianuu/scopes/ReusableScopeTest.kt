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
import org.junit.Assert.*
import org.junit.Test

class ReusableScopeTest {

    private val scope = ReusableScope()
    private val listener = TestCloseListener()

    @Test
    fun testScopeNeverClosedOnClear() {
        assertFalse(scope.isClosed)
        scope.clear()
        assertFalse(scope.isClosed)
        scope.close()
        assertTrue(scope.isClosed)
    }

    @Test
    fun testListenerClearedOnClear() {
        scope.onClose(listener)
        assertEquals(0, listener.closeCalls)

        scope.clear()
        assertEquals(1, listener.closeCalls)

        scope.clear()
        assertEquals(1, listener.closeCalls)
    }

    @Test
    fun testRemoveListeners() {
        val listener = TestCloseListener()

        scope.onClose(listener)
        scope.removeListener(listener)

        scope.clear()

        assertEquals(0, listener.closeCalls)
    }
}