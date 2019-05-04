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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.get
import androidx.lifecycle.set
import com.ivianuu.scopes.MutableScope
import com.ivianuu.scopes.Scope
import com.ivianuu.scopes.ScopeOwner
import java.io.Closeable

private const val KEY_SCOPE_OWNER = "com.ivianuu.scopes.android.ViewModelScopeOwner"

/**
 * Returns the [ScopeOwner] of this view model
 */
val ViewModel.scopeOwner: ScopeOwner
    get() {
        var scopeOwner: ScopeOwner? = get(KEY_SCOPE_OWNER)
        if (scopeOwner != null) {
            return scopeOwner
        }

        scopeOwner = ViewModelScopeOwner()

        set(KEY_SCOPE_OWNER, scopeOwner)

        return scopeOwner
    }

/**
 * Returns the [Scope] of this view model
 */
val ViewModel.scope: Scope get() = scopeOwner.scope

private class ViewModelScopeOwner : ScopeOwner, Closeable {

    override val scope = MutableScope()

    override fun close() {
        scope.close()
    }

}