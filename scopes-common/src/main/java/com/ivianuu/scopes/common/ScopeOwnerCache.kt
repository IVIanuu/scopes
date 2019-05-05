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

import com.ivianuu.scopes.ScopeOwner
import java.util.concurrent.ConcurrentHashMap

/**
 * A cache for [ScopeOwner]s
 */
class ScopeOwnerCache<K>(private val factory: (K) -> ScopeOwner) {

    private val scopeOwners = ConcurrentHashMap<K, ScopeOwner>()

    /**
     * Returns the [ScopeOwner] for the given [key]
     */
    fun get(key: K): ScopeOwner = scopeOwners.getOrPut(key) {
        factory(key).also { trackClose(it, key) }
    }

    private fun trackClose(scopeOwner: ScopeOwner, key: K) {
        scopeOwner.scope.addListener { scopeOwners.remove(key) }
    }
}