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

package com.ivianuu.scopes.android

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event.*
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.ivianuu.lifecycle.AbstractLifecycle
import com.ivianuu.scopes.Scope
import com.ivianuu.scopes.common.LifecycleScopesCache
import com.ivianuu.scopes.lifecycle.LifecycleScopes

private class AndroidLifecycle(lifecycle: Lifecycle) : AbstractLifecycle<Lifecycle.Event>() {
    init {
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                onEvent(event)
            }
        })
    }
}

private val lifecycleScopesStore =
    LifecycleScopesCache<Lifecycle, Lifecycle.Event>(ON_DESTROY) {
        LifecycleScopes(AndroidLifecycle(it))
    }

val Lifecycle.lifecycleScopes: LifecycleScopes<Lifecycle.Event>
    get() = lifecycleScopesStore.get(this)

fun Lifecycle.scopeFor(event: Lifecycle.Event): Scope =
    lifecycleScopes.scopeFor(event)

val Lifecycle.onCreate: Scope get() = scopeFor(ON_CREATE)

val Lifecycle.onStart: Scope get() = scopeFor(ON_START)

val Lifecycle.onResume: Scope get() = scopeFor(ON_RESUME)

val Lifecycle.onPause: Scope get() = scopeFor(ON_PAUSE)

val Lifecycle.onStop: Scope get() = scopeFor(ON_STOP)

val Lifecycle.onDestroy: Scope get() = scopeFor(ON_DESTROY)