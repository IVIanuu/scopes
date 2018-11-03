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

package com.ivianuu.scopes

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * A [Scope] which can be re used
 */
class ReusableScope : Scope {

    /**
     * This scope is never closed
     */
    override val isClosed: Boolean
        get() = false

    private var internalScope = MutableScope()

    private val lock = ReentrantLock()

    override fun addListener(listener: () -> Unit) {
        internalScope.addListener(listener)
    }

    override fun removeListener(listener: () -> Unit) {
        internalScope.removeListener(listener)
    }

    /**
     * Clears all current listeners and dispatches the close event to them
     */
    fun clear(): Unit = lock.withLock {
        internalScope.close()
        internalScope = MutableScope()
    }

}