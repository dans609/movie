package com.danshouseproject.project.moviecatalogue.helper

object ConvertTypeHelper {

    fun convertListIntToListString(vararg resourceId: Int): List<String> =
        ArrayList<String>().let { list ->
            for (index in resourceId) {
                list.add(index.toString())
            }
            return list
        }

}