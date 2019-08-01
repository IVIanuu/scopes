package com.ivianuu.scopes.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.doClear
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test

class ViewModelScopeTest {

    private class TestViewModel : ViewModel() {
        public override fun onCleared() {
            super.onCleared()
        }
    }

    @Test
    fun testViewModelScope() {
        val viewModel = TestViewModel()

        var closed = false

        viewModel.scope.onClose { closed = true }

        assertFalse(closed)
        viewModel.doClear()
        assertTrue(closed)
    }
}