package com.danshouseproject.project.moviecatalogue.view

import com.danshouseproject.project.moviecatalogue.core.components.domain.model.ListFilm

interface OnItemClickCallback {
    fun onItemClicked(data: ListFilm, position: Int)
}