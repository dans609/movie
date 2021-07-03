package com.danshouseproject.project.moviecatalogue.view

import com.danshouseproject.project.moviecatalogue.core.components.domain.model.FavoriteFilm

interface OnFavoriteItemClicked {
    fun onFavoriteItemClick(data: FavoriteFilm, viewId: Int)
}