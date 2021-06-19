package com.cristobal.simplemovielist.ui.favoritesList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cristobal.simplemovielist.R
import com.cristobal.simplemovielist.application.MainViewModel
import com.cristobal.simplemovielist.databinding.ItemFilmBinding
import com.cristobal.simplemovielist.model.Film
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavoritesListAdapter(films: MutableList<Film>, private val context: Context, val viewModel: MainViewModel) : RecyclerView.Adapter<FavoritesListAdapter.ViewHolder>() {

	private var mItems: MutableList<Film> = films

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

		val itemBinding = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ViewHolder(itemBinding)
	}

	override fun onBindViewHolder(holder: FavoritesListAdapter.ViewHolder, position: Int) {
		holder.bind(mItems[position])
	}

	override fun getItemCount(): Int = mItems.size

	fun setItems(newItems: MutableList<Film>) {
		mItems = newItems
		notifyDataSetChanged()
	}

	inner class ViewHolder(private val itemBinding: ItemFilmBinding) : RecyclerView.ViewHolder(itemBinding.root) {

		fun bind(item: Film) {

			itemBinding.titleTextView.text = item.title
			if (item.original_title != item.title) {
				itemBinding.originalTitleTextView.text = context.getString(R.string.original_title, item.original_title)
			} else {
				itemBinding.originalTitleTextView.text = ""
			}

			itemBinding.rateTextView.text = item.vote_average
			itemBinding.likeImageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.outline_favorite_black_48))
			itemBinding.overViewTextView.text = item.overview

			Glide.with(context)
					.load(viewModel.getImageFullUri(item.poster_path))
					.placeholder(R.drawable.poster_placeholder)
					.into(itemBinding.mainImage)

			itemBinding.parentView.setOnClickListener {
				viewModel.openDetailFilmFromFavorites(item)
			}

			// Here, the user only can dislike films
			itemBinding.likeImageView.setOnClickListener {
				CoroutineScope(Dispatchers.Main).launch {
					viewModel.removeFilmFromFavorites(item)
					mItems.removeAt(bindingAdapterPosition)
					notifyItemRemoved(bindingAdapterPosition)
				}
			}
		}

	}
}