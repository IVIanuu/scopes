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
import android.util.AttributeSet
import android.view.View
import com.ivianuu.scopes.android.scope
import com.ivianuu.scopes.rx.disposeBy
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * @author Manuel Wrage (IVIanuu)
 */
class MyView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        Observable.interval(1, TimeUnit.SECONDS)
            .doOnSubscribe { d { "on sub 1 my view" } }
            .doOnDispose { d { "on dispose 1 my view" } }
            .subscribe()
            .disposeBy(scope)

        Observable.interval(1, TimeUnit.SECONDS)
            .doOnSubscribe { d { "on sub 2 my view" } }
            .doOnDispose { d { "on dispose 2 my view" } }
            .subscribe()
            .disposeBy(scope)
    }
}