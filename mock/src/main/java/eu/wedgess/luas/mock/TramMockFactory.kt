package eu.wedgess.luas.mock

import eu.wedgess.luas.data.model.TramData
import eu.wedgess.luas.domain.model.TramEntity

fun getMockTramData(size: Int): List<TramData> {
    return (0 until size).map {
        TramData(dueMins = if (it == 0) "DUE" else "$it", destination = "Sandyford")
    }
}

fun getMockTramEntity(size: Int): List<TramEntity> {
    return (0 until size).map {
        TramEntity(dueMins = if (it == 0) "DUE" else "$it", destination = "Sandyford")
    }
}