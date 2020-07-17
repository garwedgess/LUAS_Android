package eu.wedgess.luas.mock

import eu.wedgess.luas.data.model.StopInformationData
import eu.wedgess.luas.data.util.responseDateFormatter
import eu.wedgess.luas.data.util.uiDateFormatter
import eu.wedgess.luas.domain.model.StopInformationEntity

fun getMockMarlboroughStopInformationData(): StopInformationData {
    return StopInformationData(
        message = "Green Line services operating normally",
        stopCode = "mar",
        stop = "Marlborough",
        created = requireNotNull(responseDateFormatter.get()?.parse("2020-07-17T17:46:40")),
        directions = getMarlboroughDirectionData()

    )
}

fun getMockStillorganStopInformationData(): StopInformationData {
    return StopInformationData(
        message = "Green Line services operating normally",
        stopCode = "sti",
        stop = "Stillorgan",
        created = requireNotNull(responseDateFormatter.get()?.parse("2020-07-17T17:46:40")),
        directions = getStillorganDirectionData()

    )
}


fun getMockMarlboroughStopInformationEntity(): StopInformationEntity {
    with(getMockMarlboroughStopInformationData()) {
        return StopInformationEntity(
            message = message,
            stopCode = stopCode,
            stopName = stop,
            requestTime = requireNotNull(uiDateFormatter.get()?.format(created)),
            inboundTrams = getMockTramEntity(directions.first { it.type == "Inbound" }.trams.size),
            outboundTrams = getMockTramEntity(directions.first { it.type == "Outbound" }.trams.size)
        )
    }
}

fun getMockStillorganStopInformationEntity(): StopInformationEntity {
    with(getMockStillorganStopInformationData()) {
        return StopInformationEntity(
            message = message,
            stopCode = stopCode,
            stopName = stop,
            requestTime = requireNotNull(uiDateFormatter.get()?.format(created)),
            inboundTrams = getMockTramEntity(directions.first { it.type == "Inbound" }.trams.size),
            outboundTrams = getMockTramEntity(directions.first { it.type == "Outbound" }.trams.size)
        )
    }
}