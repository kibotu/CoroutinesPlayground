package net.kibotu.coroutines.internal

import android.content.Context
import android.net.Uri
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import net.kibotu.mediagallery.MediaGalleryActivity
import net.kibotu.mediagallery.data.Image
import timber.log.Timber

/**
 * Created by [Jan Rabe](https://http://kibotu.net).
 */


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

suspend fun CoordinatorLayout.snack(message: String)  {
    Timber.v(message)
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
    delay(SHORT_DURATION_MS)
}

private const val SHORT_DURATION_MS = 1500L
private const val LONG_DURATION_MS = 2750L
