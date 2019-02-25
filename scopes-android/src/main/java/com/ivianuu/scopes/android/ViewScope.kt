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

import android.os.Build
import android.view.View
import com.ivianuu.scopes.AbstractScope
import com.ivianuu.scopes.OutsideScopeException
import com.ivianuu.scopes.Scope
import com.ivianuu.scopes.ScopeOwner
import com.ivianuu.scopes.common.ScopeOwnerCache

private val viewScopeOwners = ScopeOwnerCache<View> { ScopeOwner(ViewScope(it)) }

/**
 * Returns the [ScopeOwner] for this [View]
 */
val View.scopeOwner: ScopeOwner
    get() = viewScopeOwners.get(this)

/**
 * Returns the [Scope] of this dialog
 */
val View.scope: Scope
    get() = scopeOwner.scope

/**
 * A [Scope] for [View]s
 */
class ViewScope(private val view: View) : AbstractScope() {

    init {
        val isAttached =
            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && view.isAttachedToWindow)
                    || view.windowToken != null

        if (!isAttached) {
            throw OutsideScopeException("view not attached")
        }

        val listener = object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
            }

            override fun onViewDetachedFromWindow(v: View) {
                view.removeOnAttachStateChangeListener(this)
                close()
            }
        }

        view.addOnAttachStateChangeListener(listener)
    }
}