package com.cristobal.simplemovielist.ui.movieList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cristobal.simplemovielist.R
import com.cristobal.simplemovielist.application.MainViewModel
import com.cristobal.simplemovielist.databinding.ItemFilmBinding
import com.cristobal.simplemovielist.model.Film

class MovieListAdapter (private val context: Context, val viewModel: MainViewModel) :  PagingDataAdapter<Film, MovieListAdapter.ViewHolder>(FilmComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemBinding = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MovieListAdapter.ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
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

            itemBinding.overViewTextView.text = item.overview

            Glide.with(context)
                    .load(viewModel.getImageFullUri(item.poster_path))
                    .placeholder(R.drawable.poster_placeholder)
                    .into(itemBinding.mainImage)

            itemBinding.parentView.setOnClickListener {
                viewModel.openDetailFilm(item)
            }
        }
    }

    object FilmComparator : DiffUtil.ItemCallback<Film>() {
        override fun areItemsTheSame(oldItem: Film, newItem: Film) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Film, newItem: Film) =
            oldItem == newItem
    }
}