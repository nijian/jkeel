package com.github.nijian.jkeel.commons;

import org.apache.commons.io.IOUtils;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaCollection;
import org.apache.ws.commons.schema.utils.NamespacePrefixList;
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
        OutputStreamWriter osw = null;
        ByteArrayInputStream xsdInputStream = null;
        try {
            xsdInputStream = new ByteArrayInputStream(xsd);
            XmlSchemaCollection schemaCol = new XmlSchemaCollection();
            XmlSchema schema1 = schemaCol.read(new StreamSource(xsdInputStream));
            return jsonToXml(json, schema1);
        } finally {
            IOUtils.closeQuietly(osw, xsdInputStream);
        }
    }

    public static String jsonToXml(String json, XmlSchema xsd) throws JAXBException, SAXException, IOException {
        String schemaId = xsd.getId();

        ByteArrayOutputStream byteOs = new ByteArrayOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(byteOs, StandardCharsets.UTF_8);

        StringReader stringReader = new StringReader(json);
        StreamSource jsonSource = new StreamSource(stringReader);

        //try to get marshal
        MarshallerPair marshallerPair = null;//Cache.get();
        if (marshallerPair == null) {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            xsd.write(bos);
            ByteArrayInputStream xsdInputStream = new ByteArrayInputStream(bos.toByteArray());

            DynamicJAXBContext jaxbContext = DynamicJAXBContextFactory.createContextFromXSD(xsdInputStream, null, null, null);

            NamespacePrefixList namespacePrefixList = xsd.getNamespaceContext();
            Map<String, String> namespaces = new HashMap<>();
            for (String prefix : namespacePrefixList.getDeclaredPrefixes()) {
                namespaces.put(namespacePrefixList.getNamespaceURI(prefix), prefix);
            }

            JAXBUnmarshaller jsonUnmarshaller = jaxbContext.createUnmarshaller();
            jsonUnmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, "application/json");
            jsonUnmarshaller.setProperty(UnmarshallerProperties.JSON_NAMESPACE_PREFIX_MAPPER, namespaces);
            jsonUnmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, true);
            jsonUnmarshaller.setProperty(UnmarshallerProperties.JSON_ATTRIBUTE_PREFIX, "@");

            bos = new ByteArrayOutputStream();
            xsd.write(bos);
            xsdInputStream = new ByteArrayInputStream(bos.toByteArray());

            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            StreamSource xsdSource = new StreamSource(xsdInputStream);
            Schema schema = sf.newSchema(xsdSource);
            jsonUnmarshaller.setSchema(schema);

            JAXBElement<DynamicEntity> root = (JAXBElement) jsonUnmarshaller.unmarshal(jsonSource);

            JAXBMarshaller m = jaxbContext.createMarshaller();
            m.setProperty(JAXBMarshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(root, osw);

            marshallerPair = new MarshallerPair();
            marshallerPair.setMarshaller(m);
            marshallerPair.setUnmarshaller(jsonUnmarshaller);

            //put into cache with schemaId
        } else {
            JAXBMarshaller m = marshallerPair.getMarshaller();
            JAXBUnmarshaller jsonUnmarshaller = marshallerPair.getUnmarshaller();
            if (m == null || jsonUnmarshaller == null) {
                throw new RuntimeException("xxxxxx");
            }
            JAXBElement<DynamicEntity> root = (JAXBElement) jsonUnmarshaller.unmarshal(jsonSource);
            m.marshal(root, osw);
        }

        osw.flush();
        return byteOs.toString();
    }


    private static class MarshallerPair {

        private JAXBMarshaller marshaller;

        private JAXBUnmarshaller unmarshaller;

        public JAXBMarshaller getMarshaller() {
            return marshaller;
        }

        public void setMarshaller(JAXBMarshaller marshaller) {
            this.marshaller = marshaller;
        }

        public JAXBUnmarshaller getUnmarshaller() {
            return unmarshaller;
        }

        public void setUnmarshaller(JAXBUnmarshaller unmarshaller) {
            this.unmarshaller = unmarshaller;
        }
    }

}
