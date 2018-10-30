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

import com.ivianuu.scopes.Scope
import com.ivianuu.scopes.lifecycle.LifecycleScopes

/**
 * [LifecycleScopes] which re uses open [Scope]'s
 */
class CacheLifecycleScopes<T>(private val wrapped: LifecycleScopes<T>) :
    LifecycleScopes<T> by wrapped {

    private val scopes = mutableMapOf<T, Scope>()

    override fun scopeFor(event: T): Scope =
        scopes.getOrPut(event) {
            wrapped.scopeFor(event).also { trackClose(it, event) }
        }

    private fun trackClose(scope: Scope, event: T) {
        scope.addListener { scopes.remove(event) }
    }
}