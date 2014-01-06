package org.gwxtend.utils

import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import org.w3c.dom.Document
import java.io.StringWriter
import javax.xml.transform.TransformerFactory

class XmlUtils {
	
	def static getDocument(InputStream inputStream) {
		val factory = DocumentBuilderFactory.newInstance
		val builder = factory.newDocumentBuilder
		return builder.parse(inputStream)
	}	
	
	def static asContentString(Document document) {
		val source = new DOMSource(document)
		val streamResult = new StreamResult(new StringWriter())
		
		val factory = TransformerFactory.newInstance
		val transformer = factory.newTransformer
		transformer.transform(source, streamResult)
		
		return streamResult.toString
	}
	
}