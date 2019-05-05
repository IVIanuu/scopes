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

import java.util.concurrent.atomic.AtomicBoolean

/**
 * A base scope which handles listeners and the closed state and all listeners in a thread safe way
 */
abstract class AbstractScope : Scope {

    override val isClosed: Boolean get() = _closed.get()
    private val _closed = AtomicBoolean(false)

    private val listeners = mutableListOf<CloseListener>()

    override fun addListener(listener: CloseListener) {
        if (_closed.get()) {
            listener()
            return
        }

        synchronized(this) { listeners.add(listener) }
    }

    override fun removeListener(listener: CloseListener): Unit = synchronized(this) {
        listeners.remove(listener)
    }

    /**
     * Closes this scope
     */
    protected open fun close() {
        if (!_closed.getAndSet(true)) {
            val listeners = synchronized(this) {
                val tmp = listeners.toList()
                listeners.clear()
                tmp
            }
            listeners.forEach { it() }
        }
    }
}