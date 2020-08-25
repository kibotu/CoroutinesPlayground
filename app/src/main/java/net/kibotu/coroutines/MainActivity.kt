package net.kibotu.coroutines

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.kibotu.android.recyclerviewpresenter.PresenterAdapter
import net.kibotu.android.recyclerviewpresenter.PresenterModel
import net.kibotu.coroutines.internal.PhotoPresenter

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
            adapter.submitList(it.map { PresenterModel(it, R.layout.photo_presenter_item) })
        }
    }
}