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

package com.ivianuu.scopes.director

import android.view.View
import com.ivianuu.director.Controller
import com.ivianuu.director.ControllerLifecycleListener
import com.ivianuu.scopes.lifecycle.BaseLifecycle

/**
 * A [com.ivianuu.scopes.lifecycle.Lifecycle] for [Controller]'s
 */
class ControllerLifecycle(
    controller: Controller
) : BaseLifecycle<ControllerEvent>() {

    init {
        controller.addLifecycleListener(object : ControllerLifecycleListener {
            override fun preCreate(controller: Controller) {
                super.preCreate(controller)
                onEvent(ControllerEvent.CREATE)
            }

            override fun preContextAvailable(controller: Controller) {
                super.preContextAvailable(controller)
                onEvent(ControllerEvent.CONTEXT_AVAILABLE)
            }

            override fun preCreateView(controller: Controller) {
                super.preCreateView(controller)
                onEvent(ControllerEvent.CREATE_VIEW)
            }

            override fun preAttach(controller: Controller, view: View) {
                super.preAttach(controller, view)
                onEvent(ControllerEvent.ATTACH)
            }

            override fun postDetach(controller: Controller, view: View) {
                super.postDetach(controller, view)
                onEvent(ControllerEvent.DETACH)
            }

            override fun postDestroyView(controller: Controller) {
                super.postDestroyView(controller)
                onEvent(ControllerEvent.DESTROY_VIEW)
            }

            override fun postContextUnavailable(controller: Controller) {
                super.postContextUnavailable(controller)
                onEvent(ControllerEvent.CONTEXT_UNAVAILABLE)
            }

            override fun postDestroy(controller: Controller) {
                super.postDestroy(controller)
                onEvent(ControllerEvent.DESTROY)
            }
        })
    }
}