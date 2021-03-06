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
import com.ivianuu.scopes.AbstractScope
import com.ivianuu.scopes.Scope
import java.io.Closeable

private const val KEY_SCOPE = "com.ivianuu.scopes.android.ViewModelScope"

/**
 * Returns the [Scope] of this view model which get's closed when the this view model get's cleared
 */
val ViewModel.scope: Scope
    get() {
        var scope: Scope? = get(KEY_SCOPE)
        if (scope != null) {
            return scope
        }

        scope = ViewModelScope()

        set(KEY_SCOPE, scope)

        return scope
    }

private class ViewModelScope : AbstractScope(), Closeable {
    override fun close() {
        super.close()
    }
}