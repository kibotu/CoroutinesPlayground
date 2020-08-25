package net.kibotu.coroutines.internal

import android.graphics.drawable.Drawable
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.photo_presenter_item.view.*
import net.kibotu.android.recyclerviewpresenter.Adapter
import net.kibotu.android.recyclerviewpresenter.Presenter
import net.kibotu.android.recyclerviewpresenter.PresenterModel
import net.kibotu.coroutines.R

/**
 * Created by [Jan Rabe](https://http://kibotu.net).
 */
class PhotoPresenter : Presenter<String>() {

    override val layout: Int = R.layout.photo_presenter_item

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: PresenterModel<String>, position: Int, payloads: MutableList<Any>?, adapter: Adapter): Unit = with(viewHolder.itemView) {

        GlideApp.with(context.applicationContext)
            .load(item.model)
            .transition(DrawableTransitionOptions.withCrossFade())
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    progressBar.hide()
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    progressBar.hide()
                    return false
                }
            })
            .into(photo)

        progressBar.show()

        setOnClickListener {
            viewHolder
                .itemView
                .context
                .showGallery(item.model.toUri())
        }
    }
}