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

package com.ivianuu.scopes.archlivedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.ivianuu.scopes.Scope

fun <T> LiveData<T>.observe(scope: Scope, observer: Observer<T>) {
    scope.addListener { removeObserver(observer) }
    observeForever(observer)
}

fun <T> LiveData<T>.observe(scope: Scope, observer: (T) -> Unit) {
    scope.addListener { removeObserver(observer) }
    observeForever(observer)
}