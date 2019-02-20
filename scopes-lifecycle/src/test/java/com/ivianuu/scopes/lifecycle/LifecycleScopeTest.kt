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

import com.ivianuu.lifecycle.MutableLifecycle
import com.ivianuu.scopes.lifecycle.util.TestLifecycle
import com.ivianuu.scopes.lifecycle.util.TestLifecycle.CREATE
import com.ivianuu.scopes.lifecycle.util.TestLifecycle.DESTROY
import com.ivianuu.scopes.lifecycle.util.TestLifecycle.HIDE
import com.ivianuu.scopes.lifecycle.util.TestLifecycle.SHOW
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LifecycleScopeTest {

    private val lifecycle = MutableLifecycle<TestLifecycle>()

    @Test
    fun testLifecycleScopeClosing() {
        val scope = LifecycleScope(lifecycle, DESTROY)
        assertFalse(scope.isClosed)

        lifecycle.onEvent(CREATE)
        assertFalse(scope.isClosed)

        lifecycle.onEvent(SHOW)
        assertFalse(scope.isClosed)

        lifecycle.onEvent(HIDE)
        assertFalse(scope.isClosed)

        lifecycle.onEvent(DESTROY)
        assertTrue(scope.isClosed)
    }
}