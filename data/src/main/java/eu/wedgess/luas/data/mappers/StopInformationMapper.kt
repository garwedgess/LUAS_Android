package eu.wedgess.luas.data.mappers

import eu.wedgess.luas.data.model.DirectionData
import eu.wedgess.luas.data.model.StopInformationData
import eu.wedgess.luas.data.util.uiDateFormatter
import eu.wedgess.luas.domain.Mapper
import eu.wedgess.luas.domain.model.StopInformationEntity
import javax.inject.Inject

private const val INBOUND = "Inbound"
private const val OUTBOUND = "Outbound"

/**
 * Mapper used to map our StopInformationData to StopInformationEntity as we want to transform our data objects into an easier to use object.
 */
class StopInformationMapper @Inject constructor(
    private val tramMapper: TramMapper
) :
    Mapper<StopInformationEntity, StopInformationData> {

    override fun mapFromEntity(entity: StopInformationEntity): StopInformationData {
        return StopInformationData(
            message = entity.message,
            name = entity.name,
            requestTime = requireNotNull(uiDateFormatter.get()?.parse(entity.time)),
            code = entity.code,
            directions = listOf(
                DirectionData(
                    INBOUND,
                    entity.inboundTrams.map { tramMapper.mapFromEntity(it) }),
                DirectionData(OUTBOUND, entity.outboundTrams.map { tramMapper.mapFromEntity(it) })
            )
        )
    }

    override fun mapToEntity(data: StopInformationData): StopInformationEntity {
        return StopInformationEntity(
            message = data.message,
            name = data.name,
            time = requireNotNull(uiDateFormatter.get()).format(data.requestTime),
            code = data.code,
            inboundTrams = data.directions.first { it.name == INBOUND }.trams.map {
                tramMapper.mapToEntity(
                    it
                )
            },
            outboundTrams = data.directions.first { it.name == OUTBOUND }.trams.map {
                tramMapper.mapToEntity(
                    it
                )
            }
        )
    }

}