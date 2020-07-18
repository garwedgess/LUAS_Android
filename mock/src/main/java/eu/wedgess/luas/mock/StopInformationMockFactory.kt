package eu.wedgess.luas.mock

import eu.wedgess.luas.data.model.StopInformationData
import eu.wedgess.luas.data.util.responseDateFormatter
import eu.wedgess.luas.data.util.uiDateFormatter
import eu.wedgess.luas.domain.model.StopInformationEntity

fun getMockMarlboroughStopInformationData(): StopInformationData {
    return StopInformationData(
        message = "Green Line services operating normally",
        code = "mar",
        name = "Marlborough",
        requestTime = requireNotNull(responseDateFormatter.get()?.parse("2020-07-17T17:46:40")),
        directions = getMockMarlboroughDirectionData()

    )
}

fun getMockStillorganStopInformationData(): StopInformationData {
    return StopInformationData(
        message = "Green Line services operating normally",
        code = "sti",
        name = "Stillorgan",
        requestTime = requireNotNull(responseDateFormatter.get()?.parse("2020-07-17T17:46:40")),
        directions = getMockStillorganDirectionData()

    )
}


fun getMockMarlboroughStopInformationEntity(): StopInformationEntity {
    with(getMockMarlboroughStopInformationData()) {
        return StopInformationEntity(
            message = message,
            code = code,
            name = name,
            time = requireNotNull(uiDateFormatter.get()?.format(requestTime)),
            inboundTrams = getMockTramEntity(directions.first { it.name == "Inbound" }.trams.size),
            outboundTrams = getMockTramEntity(directions.first { it.name == "Outbound" }.trams.size)
        )
    }
}

fun getMockStillorganStopInformationEntity(): StopInformationEntity {
    with(getMockStillorganStopInformationData()) {
        return StopInformationEntity(
            message = message,
            code = code,
            name = name,
            time = requireNotNull(uiDateFormatter.get()?.format(requestTime)),
            inboundTrams = getMockTramEntity(directions.first { it.name == "Inbound" }.trams.size),
            outboundTrams = getMockTramEntity(directions.first { it.name == "Outbound" }.trams.size)
        )
    }
}