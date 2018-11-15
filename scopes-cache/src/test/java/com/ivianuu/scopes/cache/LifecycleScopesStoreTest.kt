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

package com.ivianuu.scopes.cache

import com.ivianuu.scopes.cache.util.TestLifecycle
import com.ivianuu.scopes.lifecycle.LifecycleScopes
import com.ivianuu.scopes.lifecycle.MutableLifecycle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class LifecycleScopesStoreTest {

    private val lifecycle = MutableLifecycle<TestLifecycle>()

    private val lifecycleScopesStore =
        LifecycleScopesStore<String, TestLifecycle>(TestLifecycle.DESTROY) {
            LifecycleScopes(lifecycle)
        }

    @Test
    fun testReusesLifecycleScopes() {
        val lifecycleScopes1 = lifecycleScopesStore.get("key")
        val lifecycleScopes2 = lifecycleScopesStore.get("key")
        assertEquals(lifecycleScopes1, lifecycleScopes2)
        lifecycle.onEvent(TestLifecycle.DESTROY)
        val lifecycleScopes3 = lifecycleScopesStore.get("key")
        assertNotEquals(lifecycleScopes1, lifecycleScopes3)
    }
}