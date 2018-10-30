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

package com.ivianuu.scopes.rx

import com.ivianuu.scopes.Scope
import io.reactivex.Completable

/**
 * Completes when [this] gets closed
 */
val Scope.onClose: Completable
    get() = Completable.create { e ->
        val listener = {
            if (!e.isDisposed) {
                e.onComplete()
            }
        }

        e.setCancellable(listener)

        if (!e.isDisposed) {
            addListener(listener)
        }
    }