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

import com.ivianuu.scopes.CloseListener
import com.ivianuu.scopes.Scope
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableHelper
import java.util.concurrent.atomic.AtomicReference

/**
 * Invokes [Disposable.dispose] when the [scope] gets closed
 */
fun Disposable.disposeBy(scope: Scope): Disposable = ScopeDisposable(scope, this)

private class ScopeDisposable(
    private val scope: Scope,
    disposable: Disposable
) : AtomicReference<Disposable>(disposable), Disposable {

    private val listener: CloseListener = { dispose() }

    init {
        if (isDisposed) {
            dispose()
        } else {
            scope.addListener(listener)
        }
    }

    override fun isDisposed(): Boolean = DisposableHelper.isDisposed(get())

    override fun dispose() {
        if (!isDisposed) {
            scope.removeListener(listener)
            DisposableHelper.dispose(this)
        }
    }
}