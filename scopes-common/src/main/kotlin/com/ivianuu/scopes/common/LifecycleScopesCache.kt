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

import com.ivianuu.scopes.lifecycle.LifecycleScopes
import java.util.concurrent.ConcurrentHashMap

/**
 * A cache for [LifecycleScopes]s
 */
class LifecycleScopesCache<K, E>(
    private val terminationEvent: E,
    private val factory: (K) -> LifecycleScopes<E>
) {

    private val lifecycleScopes = ConcurrentHashMap<K, LifecycleScopes<E>>()

    /**
     * Returns [lifecycleScopes] for the given [key]
     */
    fun get(key: K): LifecycleScopes<E> {
        return lifecycleScopes.getOrPut(key) {
            CachingLifecycleScopes(factory(key))
                .also { trackTermination(it, key) }
        }
    }

    private fun trackTermination(scopes: LifecycleScopes<E>, key: K) {
        scopes.scopeFor(terminationEvent).addListener {
            lifecycleScopes.remove(key)
        }
    }
}