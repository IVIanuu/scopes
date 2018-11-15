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
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * @author Manuel Wrage (IVIanuu)
 */
class DefaultLifecycleScopesTest {

    private val lifecycle = MutableLifecycle<TestLifecycle>()
    private val lifecycleScopes = LifecycleScopes(lifecycle)

    @Test
    fun testScopeFor() {
        val createScope = lifecycleScopes.scopeFor(TestLifecycle.CREATE)
        val showScope = lifecycleScopes.scopeFor(TestLifecycle.SHOW)
        val hideScope = lifecycleScopes.scopeFor(TestLifecycle.HIDE)
        val destroyScope = lifecycleScopes.scopeFor(TestLifecycle.DESTROY)

        lifecycle.onEvent(TestLifecycle.CREATE)
        assertTrue(createScope.isClosed)
        assertFalse(showScope.isClosed)
        assertFalse(hideScope.isClosed)
        assertFalse(destroyScope.isClosed)

        lifecycle.onEvent(TestLifecycle.SHOW)
        assertTrue(createScope.isClosed)
        assertTrue(showScope.isClosed)
        assertFalse(hideScope.isClosed)
        assertFalse(destroyScope.isClosed)

        lifecycle.onEvent(TestLifecycle.HIDE)
        assertTrue(createScope.isClosed)
        assertTrue(showScope.isClosed)
        assertTrue(hideScope.isClosed)
        assertFalse(destroyScope.isClosed)

        lifecycle.onEvent(TestLifecycle.DESTROY)
        assertTrue(createScope.isClosed)
        assertTrue(showScope.isClosed)
        assertTrue(hideScope.isClosed)
        assertTrue(destroyScope.isClosed)
    }
}