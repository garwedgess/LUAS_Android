package eu.wedgess.luas.data.model

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import java.util.*

@Xml(name = "stopInfo")
data class StopInformationData(
    @PropertyElement(name = "message") val message: String,
    @Attribute(name = "stop") val stop: String,
    @Attribute(name = "created") val created: Date,
    @Attribute(name = "stopAbv") val stopCode: String,
    @Element(name = "direction") val directions: List<DirectionData>
)