package eu.wedgess.luas.mock

import eu.wedgess.luas.data.model.DirectionData
import eu.wedgess.luas.data.model.TramData

fun getMockMarlboroughDirectionData(): List<DirectionData> {
    return listOf(
        DirectionData("Outbound", getMockTramData(3)),
        DirectionData(
            "Inbound",
            listOf(TramData(dueMins = "", destination = "No Northbound Service"))
        )
    )
}

fun getMockStillorganDirectionData(): List<DirectionData> {
    return listOf(
        DirectionData("Outbound", getMockTramData(3)),
        DirectionData(
            "Inbound",
            getMockTramData(2)
        )
    )
}