package com.github.nijian.jkeel.commons;

import org.apache.commons.io.IOUtils;
import org.eclipse.persistence.dynamic.DynamicEntity;
import org.eclipse.persistence.jaxb.JAXBMarshaller;
import org.eclipse.persistence.jaxb.JAXBUnmarshaller;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.eclipse.persistence.jaxb.dynamic.DynamicJAXBContext;
import org.eclipse.persistence.jaxb.dynamic.DynamicJAXBContextFactory;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ConvertUtils {

    public static String jsonToXml(String json, String xsd) throws JAXBException, SAXException, IOException {
        return jsonToXml(json, xsd.getBytes(StandardCharsets.UTF_8));
    }

    public static String jsonToXml(String json, byte[] xsd) throws JAXBException, SAXException, IOException {

        ByteArrayOutputStream byteOs = null;
        OutputStreamWriter osw = null;
        ByteArrayInputStream xsdInputStream = null;

        try {
            byteOs = new ByteArrayOutputStream();
            osw = new OutputStreamWriter(byteOs, StandardCharsets.UTF_8);
            xsdInputStream = new ByteArrayInputStream(xsd);

            DynamicJAXBContext jaxbContext = DynamicJAXBContextFactory.createContextFromXSD(xsdInputStream, null, null, null);

            Map<String, String> namespaces = new HashMap<>();
            namespaces.put("https://github.com/nijian/jkeel/converter", "");

            JAXBUnmarshaller jsonUnmarshaller = jaxbContext.createUnmarshaller();
            jsonUnmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, "application/json");
            jsonUnmarshaller.setProperty(UnmarshallerProperties.JSON_NAMESPACE_PREFIX_MAPPER, namespaces);
            jsonUnmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, true);
            jsonUnmarshaller.setProperty(UnmarshallerProperties.JSON_ATTRIBUTE_PREFIX, "@");

            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            xsdInputStream = new ByteArrayInputStream(xsd);
            StreamSource xsdSource = new StreamSource(xsdInputStream);
            Schema schema = sf.newSchema(xsdSource);
            jsonUnmarshaller.setSchema(schema);

            StringReader stringReader = new StringReader(json);
            StreamSource jsonSource = new StreamSource(stringReader);
            JAXBElement<DynamicEntity> root = (JAXBElement) jsonUnmarshaller.unmarshal(jsonSource);

            JAXBMarshaller m = jaxbContext.createMarshaller();
            m.setProperty(JAXBMarshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(root, osw);

            osw.flush();

            return byteOs.toString();
        } catch (JAXBException e) {
            throw e;
        } catch (SAXException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(byteOs, osw, xsdInputStream);
        }
    }
}
