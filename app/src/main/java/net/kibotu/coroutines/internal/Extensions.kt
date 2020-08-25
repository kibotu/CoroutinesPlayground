package net.kibotu.coroutines.internal

import android.content.Context
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import net.kibotu.mediagallery.MediaGalleryActivity
import net.kibotu.mediagallery.data.Image
import timber.log.Timber

/**
 * Created by [Jan Rabe](https://http://kibotu.net).
 */

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun RecyclerView.awaitScrollEnd() {

    // If a smooth scroll has just been started, it won't actually start until the next
    // animation frame, so we'll await that first
    awaitAnimationFrame()
    // Now we can check if we're actually idle. If so, return now
    if (scrollState == RecyclerView.SCROLL_STATE_IDLE) return

    suspendCancellableCoroutine<Unit> { continuation ->

        val scrollChangedListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // Make sure we remove the listener so we don't leak the
                    // coroutine continuation
                    recyclerView.removeOnScrollListener(this)
                    // Finally, resume the coroutine
                    continuation.resume(Unit) {
                        Timber.e(it)
                    }
                }
            }
        }

        continuation.invokeOnCancellation {
            // If the coroutine is cancelled, remove the scroll listener
            removeOnScrollListener(scrollChangedListener)
            // We could also stop the scroll here if desired
        }

        addOnScrollListener(scrollChangedListener)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun View.awaitAnimationFrame() = suspendCancellableCoroutine<Unit> { continuation ->
    val runnable = Runnable {
        continuation.resume(Unit) {
            Timber.e(it)
        }
    }
    // If the coroutine is cancelled, remove the callback
    continuation.invokeOnCancellation { removeCallbacks(runnable) }
    // And finally post the runnable
    postOnAnimation(runnable)
}

fun Context.showGallery(vararg uris: Uri) = MediaGalleryActivity
    .Builder
    .with(this) {
        autoPlay = true
        isBlurrable = true
        isTranslatable = true
        isZoomable = true
        showVideoControls = true
        showVideoControlsTimeOut = 1750
        autoPlay = true
        scrollPosition = 0
        smoothScroll = true
        preload = media.size
        showPageIndicator = true
        media = uris.map { Image(uri = it) }
    }.startActivity()