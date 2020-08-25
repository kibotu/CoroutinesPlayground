package net.kibotu.coroutines.internal

import android.content.Context
import androidx.recyclerview.widget.LinearSmoothScroller

class SnapToStartSmoothScroller(context: Context) : LinearSmoothScroller(context) {
    override fun getVerticalSnapPreference(): Int = SNAP_TO_START
}