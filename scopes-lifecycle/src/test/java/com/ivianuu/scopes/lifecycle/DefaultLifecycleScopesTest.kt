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

class DefaultLifecycleScopesTest {

    private val lifecycle = MutableLifecycle<TestLifecycle>()
    private val lifecycleScopes = LifecycleScopes(lifecycle)

    @Test
    fun testScopeFor() {
        val createScope = lifecycleScopes.scopeFor(CREATE)
        val showScope = lifecycleScopes.scopeFor(SHOW)
        val hideScope = lifecycleScopes.scopeFor(HIDE)
        val destroyScope = lifecycleScopes.scopeFor(DESTROY)

        lifecycle.onEvent(CREATE)
        assertTrue(createScope.isClosed)
        assertFalse(showScope.isClosed)
        assertFalse(hideScope.isClosed)
        assertFalse(destroyScope.isClosed)

        lifecycle.onEvent(SHOW)
        assertTrue(createScope.isClosed)
        assertTrue(showScope.isClosed)
        assertFalse(hideScope.isClosed)
        assertFalse(destroyScope.isClosed)

        lifecycle.onEvent(HIDE)
        assertTrue(createScope.isClosed)
        assertTrue(showScope.isClosed)
        assertTrue(hideScope.isClosed)
        assertFalse(destroyScope.isClosed)

        lifecycle.onEvent(DESTROY)
        assertTrue(createScope.isClosed)
        assertTrue(showScope.isClosed)
        assertTrue(hideScope.isClosed)
        assertTrue(destroyScope.isClosed)
    }
}