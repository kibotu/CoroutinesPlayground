package net.kibotu.coroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import net.kibotu.coroutines.internal.createRandomImageUrl

class ImageViewModel : ViewModel() {

    val images = liveData {
        emit((0..100).map { createRandomImageUrl() })
    }
}