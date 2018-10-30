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
import androidx.lifecycle.LifecycleOwner

fun LifecycleOwner.scopeFor(event: Lifecycle.Event) =
    lifecycle.scopeFor(event)

val LifecycleOwner.onCreate get() = lifecycle.onCreate

val LifecycleOwner.onStart get() = lifecycle.onStart

val LifecycleOwner.onResume get() = lifecycle.onResume

val LifecycleOwner.onPause get() = lifecycle.onPause

val LifecycleOwner.onStop get() = lifecycle.onStop

val LifecycleOwner.onDestroy get() = lifecycle.onDestroy