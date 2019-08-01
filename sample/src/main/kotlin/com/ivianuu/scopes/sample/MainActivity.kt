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
import androidx.lifecycle.ViewModelProvider
import com.ivianuu.scopes.android.onCreate
import com.ivianuu.scopes.android.onDestroy
import com.ivianuu.scopes.android.onStop
import com.ivianuu.scopes.coroutines.cancelBy
import com.ivianuu.scopes.rx.disposeBy
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext = Job().cancelBy(onDestroy)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(MyView(this))

        val viewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            )[ScopedViewModel::class.java]

        onCreate.onClose { d { "create closed" } }
        onCreate.onClose { d { "create 2 closed" } }

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