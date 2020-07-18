package eu.wedgess.luas.data.mappers

import eu.wedgess.luas.data.model.TramData
import eu.wedgess.luas.domain.model.TramEntity
import eu.wedgess.luas.mock.getMockTramData
import eu.wedgess.luas.mock.getMockTramEntity
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TramMapperTest {

    private lateinit var mapper: TramMapper
    private lateinit var data: TramData
    private lateinit var entity: TramEntity

    @Before
    fun setUp() {
        mapper = TramMapper()
        data = getMockTramData(1)[0]
        entity = getMockTramEntity(1)[0]
    }

    @Test
    fun `mapsFrom destination`() {
        val result = mapper.mapFromEntity(entity)
        assertEquals(entity.destination, result.destination)
    }

    @Test
    fun `mapsFrom dueMins`() {
        val result = mapper.mapFromEntity(entity)
        assertEquals(entity.due, result.dueMins)
    }


    @Test
    fun `mapsTo destination`() {
        val result = mapper.mapToEntity(data)
        assertEquals(data.destination, result.destination)
    }

    @Test
    fun `mapsTo dueMins`() {
        val result = mapper.mapToEntity(data)
        assertEquals(data.dueMins, result.due)
    }
}