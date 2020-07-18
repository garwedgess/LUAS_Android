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
    fun `mapsFrom name`() {
        val result = mapper.mapFromEntity(entity)
        assertEquals(entity.name, result.name)
    }

    @Test
    fun `mapsFrom code`() {
        val result = mapper.mapFromEntity(entity)
        assertEquals(entity.code, result.code)
    }

    @Test
    fun `mapsFrom message`() {
        val result = mapper.mapFromEntity(entity)
        assertEquals(entity.message, result.message)
    }

    @Test
    fun `mapsFrom requestTime`() {
        val result = mapper.mapFromEntity(entity)
        assertEquals(entity.time, uiDateFormatter.get()?.format(result.requestTime))
    }

    @Test
    fun `mapsFrom outboundTrams (size)`() {
        val result = mapper.mapFromEntity(entity)
        assertEquals(
            entity.outboundTrams.size,
            result.directions.first { it.name == "Outbound" }.trams.size
        )
    }

    @Test
    fun `mapsFrom inboundTrams (size)`() {
        val result = mapper.mapFromEntity(entity)
        assertEquals(
            entity.inboundTrams.size,
            result.directions.first { it.name == "Inbound" }.trams.size
        )
    }


    @Test
    fun `mapsTo name`() {
        val result = mapper.mapToEntity(data)
        assertEquals(data.name, result.name)
    }

    @Test
    fun `mapsTo code`() {
        val result = mapper.mapToEntity(data)
        assertEquals(data.code, result.code)
    }

    @Test
    fun `mapsTo message`() {
        val result = mapper.mapToEntity(data)
        assertEquals(data.message, result.message)
    }

    @Test
    fun `mapsTo requestTime`() {
        val result = mapper.mapToEntity(data)
        assertEquals(data.requestTime, uiDateFormatter.get()?.parse(result.time))
    }

    @Test
    fun `mapsTo outboundTrams (size)`() {
        val result = mapper.mapToEntity(data)
        assertEquals(
            data.directions.first { it.name == "Outbound" }.trams.size,
            result.outboundTrams.size
        )
    }

    @Test
    fun `mapsTo inboundTrams (size)`() {
        val result = mapper.mapToEntity(data)
        assertEquals(
            data.directions.first { it.name == "Inbound" }.trams.size,
            result.inboundTrams.size
        )
    }
}