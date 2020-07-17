package eu.wedgess.luas.data.util

import com.tickaroo.tikxml.TypeConverter
import java.util.*

class TikXMLDateFormatter : TypeConverter<Date> {

    @Throws(Exception::class)
    override fun read(value: String): Date {
        return requireNotNull(responseDateFormatter.get()?.parse(value))
    }

    @Throws(Exception::class)
    override fun write(value: Date): String {
        return requireNotNull(responseDateFormatter.get()?.format(value))
    }
}