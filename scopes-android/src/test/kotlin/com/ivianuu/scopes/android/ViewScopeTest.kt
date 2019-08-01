package com.ivianuu.scopes.android

import android.app.Activity
import android.view.View
import android.widget.FrameLayout
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ivianuu.scopes.OutsideScopeException
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class ViewScopeTest {

    class TestActivity : Activity()

    @Test
    fun testViewScopeCloses() {
        val scenario = ActivityScenario.launch(TestActivity::class.java)

        scenario.onActivity {
            val parent = FrameLayout(it)
            it.setContentView(parent)

            val view = View(it)
            parent.addView(view)

            var closed = false
            view.scope.onClose { closed = true }

            assertFalse(closed)
            parent.removeView(view)
            assertTrue(closed)
        }
    }

    @Test(expected = OutsideScopeException::class)
    fun testViewScopeOnlyAccessibleWhileAttached() {
        val scenario = ActivityScenario.launch(TestActivity::class.java)

        scenario.onActivity {
            val view = View(it)
            view.scope
        }
    }

}