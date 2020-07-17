package eu.wedgess.luas.data.model

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "direction")
data class DirectionData(
    @Attribute(name = "name") val type: String,
    @Element(name = "tram") val trams: List<TramData>
)