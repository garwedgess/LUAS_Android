package eu.wedgess.luas.data.mappers

import eu.wedgess.luas.data.model.TramData
import eu.wedgess.luas.domain.Mapper
import eu.wedgess.luas.domain.model.TramEntity

/***
 * Mapper used to map our TramData to TramEntity. This mapper is used by {@link StopInformationMapper}
 ***/
class TramMapper : Mapper<TramEntity, TramData> {

    override fun mapFromEntity(entity: TramEntity): TramData {
        return TramData(
            dueMins = entity.dueMins,
            destination = entity.destination
        )
    }

    override fun mapToEntity(data: TramData): TramEntity {
        return TramEntity(
            dueMins = data.dueMins,
            destination = data.destination
        )
    }

}