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

package com.ivianuu.scopes.common

import com.ivianuu.scopes.Scope
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * A property that gets cleaned up when a scope gets closed.
 * */
class AutoClearedValue<T>(scope: Scope) : ReadWriteProperty<Any, T> {

    private var value: T? = null

    init {
        scope.addListener { value = null }
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): T =
        value ?: throw IllegalStateException(
            "should never call auto-cleared-value get when it might not be available"
        )

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        this.value = value
    }
}

/**
 * Returns a [AutoClearedValue] which clears the internal value when the [scope] gets closed
 */
fun <T : Any> autoCleared(scope: Scope): ReadWriteProperty<Any, T> = AutoClearedValue(scope)