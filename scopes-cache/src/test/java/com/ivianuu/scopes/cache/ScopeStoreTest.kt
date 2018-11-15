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

import com.ivianuu.scopes.MutableScope
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

/**
 * @author Manuel Wrage (IVIanuu)
 */
class ScopeStoreTest {

    private val scopeStore = ScopeStore<String> { MutableScope() }

    @Test
    fun testReusesScopeWhileOpen() {
        val scope1 = scopeStore.get("key")
        val scope2 = scopeStore.get("key")
        assertEquals(scope1, scope2)
        (scope1 as MutableScope).close()
        val scope3 = scopeStore.get("key")
        assertNotEquals(scope1, scope3)
    }
}