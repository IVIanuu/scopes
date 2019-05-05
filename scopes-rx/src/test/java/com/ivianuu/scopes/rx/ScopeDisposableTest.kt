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

package com.ivianuu.scopes.rx

import com.ivianuu.scopes.MutableScope
import io.reactivex.Observable
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test
import java.util.concurrent.TimeUnit

class ScopeDisposableTest {

    @Test
    fun testScopeDisposable() {
        val scope = MutableScope()

        val srcDisposable = Observable.interval(1, TimeUnit.SECONDS)
            .subscribe()
        val scopeDisposable = srcDisposable.disposeBy(scope)

        assertFalse(scopeDisposable.isDisposed)
        assertFalse(srcDisposable.isDisposed)

        scope.close()

        assertTrue(scopeDisposable.isDisposed)
        assertTrue(srcDisposable.isDisposed)
    }

}