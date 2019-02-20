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

package com.ivianuu.scopes.lifecycle

import com.ivianuu.lifecycle.Lifecycle
import com.ivianuu.lifecycle.LifecycleListener
import com.ivianuu.scopes.AbstractScope

/**
 * A scope which closes once the [event] has happened in the [lifecycle]
 */
class LifecycleScope<T>(
    private val lifecycle: Lifecycle<T>,
    private val event: T
) : AbstractScope(), LifecycleListener<T> {

    init {
        lifecycle.addListener(this)
    }

    override fun invoke(e: T) {
        if (e == event) {
            lifecycle.removeListener(this)
            close()
        }
    }
}