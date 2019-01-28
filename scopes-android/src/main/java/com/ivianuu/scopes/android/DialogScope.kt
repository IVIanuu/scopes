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

import android.app.Dialog
import android.os.Build
import android.view.View
import com.ivianuu.scopes.AbstractScope
import com.ivianuu.scopes.OutsideLifecycleException
import com.ivianuu.scopes.Scope
import com.ivianuu.scopes.cache.ScopeStore

private val dialogScopes = ScopeStore<Dialog> { DialogScope(it) }

/**
 * A [Scope] which will be re used on this instance
 */
val Dialog.scope: Scope
    get() = dialogScopes.get(this)

/**
 * A [Scope] for [Dialog]s
 */
class DialogScope(dialog: Dialog) : AbstractScope() {

    init {
        val window = dialog.window ?: throw OutsideLifecycleException("not attached to window")

        val isAttached =
            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && window.decorView.isAttachedToWindow)
                    || window.decorView.windowToken != null

        if (!isAttached) {
            throw OutsideLifecycleException("not attached to window")
        }

        val listener = object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
            }

            override fun onViewDetachedFromWindow(v: View) {
                window.decorView.removeOnAttachStateChangeListener(this)
                close()
            }
        }

        window.decorView.addOnAttachStateChangeListener(listener)
    }
}