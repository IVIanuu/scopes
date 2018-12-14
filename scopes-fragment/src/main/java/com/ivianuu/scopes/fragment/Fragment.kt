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

package com.ivianuu.scopes.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.ivianuu.scopes.android.lifecycle.*

fun Fragment.viewScopeFor(event: Lifecycle.Event) = viewLifecycleOwner.scopeFor(event)

val Fragment.viewOnCreate
    get() = viewLifecycleOwner.onCreate

val Fragment.viewOnStart
    get() = viewLifecycleOwner.onStart

val Fragment.viewOnResume
    get() = viewLifecycleOwner.onResume

val Fragment.viewOnPause
    get() = viewLifecycleOwner.onPause

val Fragment.viewOnStop
    get() = viewLifecycleOwner.onStop

val Fragment.viewOnDestroy
    get() = viewLifecycleOwner.onDestroy