package org.gwxtend.utils;

import java.io.InputStream;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.w3c.dom.Document;

@SuppressWarnings("all")
public class XmlUtils {
  public static Document getDocument(final InputStream inputStream) {
    try {
      final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      final DocumentBuilder builder = factory.newDocumentBuilder();
      return builder.parse(inputStream);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static String asContentString(final Document document) {
    try {
      DOMSource _dOMSource = new DOMSource(document);
      final DOMSource source = _dOMSource;
      StringWriter _stringWriter = new StringWriter();
      StreamResult _streamResult = new StreamResult(_stringWriter);
      final StreamResult streamResult = _streamResult;
      final TransformerFactory factory = TransformerFactory.newInstance();
      final Transformer transformer = factory.newTransformer();
      transformer.transform(source, streamResult);
      return streamResult.toString();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
