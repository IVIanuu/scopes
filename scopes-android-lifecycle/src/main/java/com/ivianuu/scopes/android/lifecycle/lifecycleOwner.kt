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
import androidx.lifecycle.LifecycleOwner
import com.ivianuu.scopes.Scope
import com.ivianuu.scopes.lifecycle.LifecycleScopes

val LifecycleOwner.lifecycleScopes: LifecycleScopes<Lifecycle.Event> get() = lifecycle.lifecycleScopes

fun LifecycleOwner.scopeFor(event: Lifecycle.Event): Scope =
    lifecycle.scopeFor(event)

val LifecycleOwner.onCreate: Scope get() = lifecycle.onCreate

val LifecycleOwner.onStart: Scope get() = lifecycle.onStart

val LifecycleOwner.onResume: Scope get() = lifecycle.onResume

val LifecycleOwner.onPause: Scope get() = lifecycle.onPause

val LifecycleOwner.onStop: Scope get() = lifecycle.onStop

val LifecycleOwner.onDestroy: Scope get() = lifecycle.onDestroy