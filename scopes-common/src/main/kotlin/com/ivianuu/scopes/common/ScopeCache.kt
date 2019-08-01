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

import com.ivianuu.scopes.Scope

/**
 * A cache for [Scope]s
 */
class ScopeCache<K>(private val factory: (K) -> Scope) {

    private val scopes = hashMapOf<K, Scope>()

    /**
     * Returns the [Scope] for the given [key]
     */
    fun get(key: K): Scope {
        var scope = synchronized(scopes) { scopes[key] }
        if (scope == null) {
            scope = factory(key)
            scope.onClose {
                synchronized(scopes) {
                    scopes -= key
                }
            }
            synchronized(scopes) { scopes[key] = scope }
        }

        return scope
    }

}