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

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.ivianuu.scopes.android.lifecycle.onCreate
import com.ivianuu.scopes.android.lifecycle.onDestroy
import com.ivianuu.scopes.android.lifecycle.onStop
import com.ivianuu.scopes.coroutines.cancelBy
import com.ivianuu.scopes.rx.disposeBy
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private val job =
        Job().cancelBy(onDestroy)

    override val coroutineContext: CoroutineContext
        get() = job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(MyView(this))

        val viewModel = ViewModelProviders.of(this)[ScopedViewModel::class.java]

        onCreate.addListener { d { "create closed" } }
        onCreate.addListener { d { "create 2 closed" } }

        Observable.interval(1, TimeUnit.SECONDS)
            .doOnSubscribe { d { "on sub stop" } }
            .doOnDispose { d { "on dispose stop" } }
            .subscribe()
            .disposeBy(onStop)

        Observable.interval(1, TimeUnit.SECONDS)
            .doOnSubscribe { d { "on sub destroy" } }
            .doOnDispose { d { "on dispose destroy" } }
            .subscribe()
            .disposeBy(onDestroy)
    }
}