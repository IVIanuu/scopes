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

package com.ivianuu.scopes.android.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event.ON_CREATE
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.Lifecycle.Event.ON_PAUSE
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.Lifecycle.Event.ON_START
import androidx.lifecycle.Lifecycle.Event.ON_STOP
import com.ivianuu.lifecycle.android.lifecycle.AndroidLifecycle
import com.ivianuu.scopes.Scope
import com.ivianuu.scopes.cache.LifecycleScopesStore
import com.ivianuu.scopes.lifecycle.LifecycleScopes

private val lifecycleScopesStore =
    LifecycleScopesStore<Lifecycle, Lifecycle.Event>(ON_DESTROY) {
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