package dev.vincenzocostagliola.designsystem.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import timber.log.Timber

@Composable
fun <LO : LifecycleObserver> LO.observeLifecycle(lifecycle: Lifecycle) {
  DisposableEffect(lifecycle) {
    Timber.d("LifecycleObserver - currentState: ${lifecycle.currentState}")
    lifecycle.addObserver(this@observeLifecycle)
    onDispose {
      lifecycle.removeObserver(this@observeLifecycle)
    }
  }
}