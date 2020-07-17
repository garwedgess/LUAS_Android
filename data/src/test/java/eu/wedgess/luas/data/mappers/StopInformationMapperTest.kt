package eu.wedgess.luas.data.mappers

import eu.wedgess.luas.data.model.StopInformationData
import eu.wedgess.luas.data.util.uiDateFormatter
import eu.wedgess.luas.domain.model.StopInformationEntity
import eu.wedgess.luas.mock.getMockStillorganStopInformationData
import eu.wedgess.luas.mock.getMockStillorganStopInformationEntity
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class StopInformationMapperTest {

    private lateinit var mapper: StopInformationMapper
    private lateinit var data: StopInformationData
    private lateinit var entity: StopInformationEntity

    @Before
    fun setUp() {
        mapper = StopInformationMapper(TramMapper())
        data = getMockStillorganStopInformationData()
        entity = getMockStillorganStopInformationEntity()
    }

    @Test
    fun `mapsFrom stopName`() {
        val result = mapper.mapFromEntity(entity)
        assertEquals(entity.stopName, result.stop)
    }

    @Test
    fun `mapsFrom stopCode`() {
        val result = mapper.mapFromEntity(entity)
        assertEquals(entity.stopCode, result.stopCode)
    }

    @Test
    fun `mapsFrom message`() {
        val result = mapper.mapFromEntity(entity)
        assertEquals(entity.message, result.message)
    }

    @Test
    fun `mapsFrom requestTime`() {
        val result = mapper.mapFromEntity(entity)
        assertEquals(entity.requestTime, uiDateFormatter.get()?.format(result.created))
    }

    @Test
    fun `mapsFrom outboundTrams (size)`() {
        val result = mapper.mapFromEntity(entity)
        assertEquals(
            entity.outboundTrams.size,
            result.directions.first { it.type == "Outbound" }.trams.size
        )
    }

    @Test
    fun `mapsFrom inboundTrams (size)`() {
        val result = mapper.mapFromEntity(entity)
        assertEquals(
            entity.inboundTrams.size,
            result.directions.first { it.type == "Inbound" }.trams.size
        )
    }


    @Test
    fun `mapsTo stop`() {
        val result = mapper.mapToEntity(data)
        assertEquals(data.stop, result.stopName)
    }

    @Test
    fun `mapsTo stopCode`() {
        val result = mapper.mapToEntity(data)
        assertEquals(data.stopCode, result.stopCode)
    }

    @Test
    fun `mapsTo message`() {
        val result = mapper.mapToEntity(data)
        assertEquals(data.message, result.message)
    }

    @Test
    fun `mapsTo created`() {
        val result = mapper.mapToEntity(data)
        assertEquals(data.created, uiDateFormatter.get()?.parse(result.requestTime))
    }

    @Test
    fun `mapsTo outboundTrams (size)`() {
        val result = mapper.mapToEntity(data)
        assertEquals(
            data.directions.first { it.type == "Outbound" }.trams.size,
            result.outboundTrams.size
        )
    }

    @Test
    fun `mapsTo inboundTrams (size)`() {
        val result = mapper.mapToEntity(data)
        assertEquals(
            data.directions.first { it.type == "Inbound" }.trams.size,
            result.inboundTrams.size
        )
    }
}