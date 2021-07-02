package com.danshouseproject.project.moviecatalogue.view

import com.danshouseproject.project.moviecatalogue.model.FavoriteFilm

interface OnFavoriteItemClicked {
    fun onFavoriteItemClick(data: FavoriteFilm, viewId: Int)
}