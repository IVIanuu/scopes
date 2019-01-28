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
 * A simple scope which exposes a [close] function to close it.
 */
class MutableScope : Scope {

    override val isClosed get() = lock.withLock { _closed }
    private var _closed = false

    private val lock = ReentrantLock()

    private val listeners = mutableListOf<CloseListener>()

    override fun addListener(listener: CloseListener): Unit = lock.withLock {
        if (_closed) {
            listener()
            return@withLock
        }

        if (!listeners.contains(listener)) {
            listeners.add(listener)
        }
    }

    override fun removeListener(listener: CloseListener): Unit = lock.withLock {
        listeners.remove(listener)
    }

    /**
     * Closes the scope
     */
    fun close(): Unit = lock.withLock {
        if (!_closed) {
            listeners.toList().forEach { it() }
            listeners.clear()
        }
    }
}