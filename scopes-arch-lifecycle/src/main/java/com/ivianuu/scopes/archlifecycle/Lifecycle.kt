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

package com.ivianuu.scopes.archlifecycle

import androidx.lifecycle.Lifecycle
import com.ivianuu.scopes.cache.LifecycleScopesStore
import com.ivianuu.scopes.lifecycle.LifecycleScopes

private val lifecycleScopesStore =
    LifecycleScopesStore<Lifecycle, Lifecycle.Event>(Lifecycle.Event.ON_DESTROY) {
        LifecycleScopes(AndroidLifecycle(it))
    }

val Lifecycle.lifecycleScopes: LifecycleScopes<Lifecycle.Event>
    get() = lifecycleScopesStore.get(this)

fun Lifecycle.scopeFor(event: Lifecycle.Event) =
    lifecycleScopes.scopeFor(event)

val Lifecycle.onCreate get() = scopeFor(Lifecycle.Event.ON_CREATE)

val Lifecycle.onStart get() = scopeFor(Lifecycle.Event.ON_START)

val Lifecycle.onResume get() = scopeFor(Lifecycle.Event.ON_RESUME)

val Lifecycle.onPause get() = scopeFor(Lifecycle.Event.ON_PAUSE)

val Lifecycle.onStop get() = scopeFor(Lifecycle.Event.ON_STOP)

val Lifecycle.onDestroy get() = scopeFor(Lifecycle.Event.ON_DESTROY)