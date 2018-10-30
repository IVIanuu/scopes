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

package com.ivianuu.scopes.sample

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ivianuu.scopes.lifecycle.Lifecycle
import com.ivianuu.scopes.lifecycle.MutableLifecycle

enum class FragmentEvent {
    ATTACH,
    CREATE,
    CREATE_VIEW,
    START,
    RESUME,
    PAUSE,
    STOP,
    DESTROY_VIEW,
    DESTROY,
    DETACH
}

/**
 * @author Manuel Wrage (IVIanuu)
 */
class ScopeFragment : Fragment() {

    val lifecycle: Lifecycle<FragmentEvent> get() = _lifecycle
    private val _lifecycle = MutableLifecycle<FragmentEvent>()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        _lifecycle.onEvent(FragmentEvent.ATTACH)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _lifecycle.onEvent(FragmentEvent.CREATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _lifecycle.onEvent(FragmentEvent.CREATE_VIEW)
    }

    override fun onStart() {
        super.onStart()
        _lifecycle.onEvent(FragmentEvent.START)
    }

    override fun onResume() {
        super.onResume()
        _lifecycle.onEvent(FragmentEvent.RESUME)
    }

    override fun onPause() {
        super.onPause()
        _lifecycle.onEvent(FragmentEvent.PAUSE)
    }

    override fun onStop() {
        super.onStop()
        _lifecycle.onEvent(FragmentEvent.STOP)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _lifecycle.onEvent(FragmentEvent.DESTROY_VIEW)
    }

    override fun onDestroy() {
        super.onDestroy()
        _lifecycle.onEvent(FragmentEvent.DESTROY)
    }

    override fun onDetach() {
        super.onDetach()
        _lifecycle.onEvent(FragmentEvent.DETACH)
    }
}