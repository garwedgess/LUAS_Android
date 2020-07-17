package eu.wedgess.luas.data.model

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "tram")
data class TramData(
    @Attribute(name = "dueMins") val dueMins: String,
    @Attribute(name = "destination") val destination: String
)