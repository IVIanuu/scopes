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

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.ivianuu.scopes.Scope

fun Fragment.viewScopeFor(event: Lifecycle.Event): Scope = viewLifecycleOwner.scopeFor(event)

val Fragment.viewOnCreate: Scope
    get() = viewLifecycleOwner.onCreate

val Fragment.viewOnStart: Scope
    get() = viewLifecycleOwner.onStart

val Fragment.viewOnResume: Scope
    get() = viewLifecycleOwner.onResume

val Fragment.viewOnPause: Scope
    get() = viewLifecycleOwner.onPause

val Fragment.viewOnStop: Scope
    get() = viewLifecycleOwner.onStop

val Fragment.viewOnDestroy: Scope
    get() = viewLifecycleOwner.onDestroy