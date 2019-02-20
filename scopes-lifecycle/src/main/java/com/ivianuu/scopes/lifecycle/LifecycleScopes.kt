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

import com.ivianuu.lifecycle.Lifecycle
import com.ivianuu.scopes.Scope

/**
 * Lifecycle scopes
 */
interface LifecycleScopes<T> {

    /**
     * The lifecycle of this component
     */
    val lifecycle: Lifecycle<T>

    /**
     * Returns a [Scope] which will be closed when the [event] occurs in the [lifecycle]
     */
    fun scopeFor(event: T): Scope = LifecycleScope(lifecycle, event)
}

/**
 * Returns new [LifecycleScopes] which uses the [lifecycle] internally
 */
fun <T> LifecycleScopes(lifecycle: Lifecycle<T>): LifecycleScopes<T> =
    DefaultLifecycleScopes(lifecycle)

class DefaultLifecycleScopes<T>(override val lifecycle: Lifecycle<T>) : LifecycleScopes<T>