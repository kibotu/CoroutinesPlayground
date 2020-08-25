package net.kibotu.coroutines

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import net.kibotu.android.recyclerviewpresenter.PresenterAdapter
import net.kibotu.android.recyclerviewpresenter.PresenterModel
import net.kibotu.coroutines.internal.PhotoPresenter
import net.kibotu.coroutines.internal.SnapToStartSmoothScroller
import net.kibotu.coroutines.internal.showGallery
import net.kibotu.coroutines.internal.snack
import timber.log.Timber


/**
 * Created by [Jan Rabe](https://http://kibotu.net).
 */

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<ImageViewModel>()

    private val adapter = PresenterAdapter()
        .apply {
            registerPresenter(PhotoPresenter())
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list.adapter = adapter

        viewModel.images.observe(this) {
            adapter.submitList(it.map {
                PresenterModel(it, R.layout.photo_presenter_item, onItemClickListener = { item, _, position ->
                    scrollTo(position, item)
                })
            })
        }

        animateTitle()
    }

    private fun animateTitle() {
        lifecycleScope.launch {
            // Make the view invisible and set some new text
            titleView.isInvisible = true
            titleView.text = "Hi everyone!"

            // Wait for the next layout pass to know
            // the height of the view
            titleView.awaitNextLayout()

            // Layout has happened!
            // We can now make the view visible, translate it up, and then animate it
            // back down
            titleView.isVisible = true
            titleView.translationY = -titleView.height.toFloat()
            titleView.animate().translationY(0f)
        }
    }

    private fun scrollTo(position: Int, model: String) {

        val startTime = System.currentTimeMillis()

        Timber.v("scrolling to $position starting...")

        startSmoothScroll(position)

        lifecycleScope.launch {

            list.awaitScrollEnd()

            coordinator.snack("Scrolling to $position ended duration=${System.currentTimeMillis() - startTime} ms")

            showGallery(model.toUri())
        }
    }

    private fun startSmoothScroll(position: Int) {
        list.layoutManager?.startSmoothScroll(SnapToStartSmoothScroller(this).apply { targetPosition = position })
    }

}