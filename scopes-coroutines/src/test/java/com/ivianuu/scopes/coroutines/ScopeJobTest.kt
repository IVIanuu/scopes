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

package com.ivianuu.scopes.coroutines

import com.ivianuu.scopes.MutableScope
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Test

class ScopeJobTest {

    @Test
    fun testScopeJob() {
        val scope = MutableScope()

        val srcJob = GlobalScope.launch { delay(5000) }
        val scopeJob = srcJob.cancelBy(scope)

        assertFalse(scopeJob.isCancelled)
        assertFalse(srcJob.isCancelled)

        scope.close()

        assertTrue(scopeJob.isCancelled)
        assertTrue(srcJob.isCancelled)
    }

}