package eu.wedgess.luas.mock

import java.io.File

abstract class XmlResponseLoader {

    companion object {
        const val ROOT_XML_DIRECTORY = "responses/xml"
    }

    fun getXml(path: String): String {
        return this.javaClass.classLoader?.getResource(path)?.let { url ->
            String(File(url.path).readBytes())
        } ?: throw IllegalArgumentException("$path not found!")
    }
}