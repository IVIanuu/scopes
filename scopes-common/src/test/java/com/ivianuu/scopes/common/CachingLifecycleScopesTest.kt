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

package com.ivianuu.scopes.common

import com.ivianuu.lifecycle.MutableLifecycle
import com.ivianuu.scopes.common.util.TestLifecycle
import com.ivianuu.scopes.lifecycle.LifecycleScopes
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class CachingLifecycleScopesTest {

    private val lifecycle = MutableLifecycle<TestLifecycle>()
    private val lifecycleScopes = LifecycleScopes(lifecycle)
    private val cacheLifecycleScopes = CachingLifecycleScopes(lifecycleScopes)

    @Test
    fun testReusesScopes() {
        val scope1 = cacheLifecycleScopes.scopeFor(TestLifecycle.CREATE)
        val scope2 = cacheLifecycleScopes.scopeFor(TestLifecycle.CREATE)
        assertEquals(scope1, scope2)
        lifecycle.onEvent(TestLifecycle.CREATE)
        val scope3 = cacheLifecycleScopes.scopeFor(TestLifecycle.CREATE)
        assertNotEquals(scope1, scope3)
    }
}