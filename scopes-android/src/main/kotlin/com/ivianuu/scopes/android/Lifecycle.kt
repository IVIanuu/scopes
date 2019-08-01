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
import com.ivianuu.scopes.MutableScope
import com.ivianuu.scopes.Scope
import java.util.concurrent.ConcurrentHashMap

private val scopesByLifecycle =
    ConcurrentHashMap<Lifecycle, ConcurrentHashMap<Lifecycle.Event, Scope>>()

internal fun Lifecycle.scopeFor(event: Lifecycle.Event): Scope {
    val scopes = scopesByLifecycle.getOrPut(this) {
        ConcurrentHashMap<Lifecycle.Event, Scope>().also {
            addObserver(LifecycleEventObserver { _, _ ->
                if (currentState == Lifecycle.State.DESTROYED) {
                    scopesByLifecycle.remove(this)
                }
            })
        }
    }

    return scopes.getOrPut(event) {
        val scope = MutableScope()
        addObserver(LifecycleEventObserver { _, currentEvent ->
            if (event == currentEvent) {
                scope.close()
                scopes.remove(event)
            }
        })
        scope
    }
}

val Lifecycle.onCreate: Scope get() = scopeFor(ON_CREATE)

val Lifecycle.onStart: Scope get() = scopeFor(ON_START)

val Lifecycle.onResume: Scope get() = scopeFor(ON_RESUME)

val Lifecycle.onPause: Scope get() = scopeFor(ON_PAUSE)

val Lifecycle.onStop: Scope get() = scopeFor(ON_STOP)

val Lifecycle.onDestroy: Scope get() = scopeFor(ON_DESTROY)