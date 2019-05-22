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

import com.ivianuu.scopes.CloseListener
import com.ivianuu.scopes.Scope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job

/**
 * Cancels this job when the [scope] gets closed
 */
fun Job.cancelBy(scope: Scope): Job = ScopeJob(scope, this)

private class ScopeJob(
    private val scope: Scope,
    private val job: Job
) : Job by job {

    private val listener: CloseListener = { cancel() }

    init {
        if (isCancelled) {
            cancel(null)
        } else {
            scope.addListener(listener)
        }
    }

    override fun cancel(cause: CancellationException?) {
        scope.removeListener(listener)
        job.cancel(cause)
    }

}