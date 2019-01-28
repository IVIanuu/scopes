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

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * Base lifecycle which handles listeners in a thread safe way
 */
abstract class AbstractLifecycle<T> : Lifecycle<T> {

    private val lock = ReentrantLock()

    private val listeners = mutableListOf<LifecycleListener<T>>()

    override fun addListener(listener: LifecycleListener<T>): Unit = lock.withLock {
        if (!listeners.contains(listener)) {
            listeners.add(listener)
        }
    }

    override fun removeListener(listener: LifecycleListener<T>): Unit = lock.withLock {
        listeners.remove(listener)
    }

    /**
     * Sends the [event] to all listeners
     */
    protected open fun onEvent(event: T) {
        lock.withLock { listeners.toList().forEach { it(event) } }
    }
}