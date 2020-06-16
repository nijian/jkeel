package com.github.nijian.jkeel.converter;

import org.eclipse.persistence.dynamic.DynamicEntity;
import org.eclipse.persistence.jaxb.JAXBMarshaller;
import org.eclipse.persistence.jaxb.JAXBUnmarshaller;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.eclipse.persistence.jaxb.dynamic.DynamicJAXBContext;
import org.eclipse.persistence.jaxb.dynamic.DynamicJAXBContextFactory;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBElement;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Json2Xml {

    public String convert(String json, String xsd) throws Exception {

        ByteArrayInputStream xsdInputStream = new ByteArrayInputStream(xsd.getBytes(StandardCharsets.UTF_8));
        DynamicJAXBContext jaxbContext1 = DynamicJAXBContextFactory.createContextFromXSD(xsdInputStream, new MyEntityResolver(), null, null);

//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
////        InputStream iStream = classLoader.getResourceAsStream("resource/MySchema.xsd");
//        Map<String, Object> properties = new HashMap<>();
//        properties.put(DynamicJAXBContextFactory.XML_SCHEMA_KEY, xsdInputStream);
//        DynamicJAXBContext jaxbContext1 = (DynamicJAXBContext) JAXBContext.newInstance("com.github.nijian.jkeel.converter", classLoader, properties);


//        DynamicJAXBContext jaxbContext1 = DynamicJAXBContextFactory.createContext("com/github/nijian/jkeel/converter/", null, null);

        Map<String, String> namespaces = new HashMap<>();
        namespaces.put("https://github.com/nijian/jkeel", "x");
//        namespaces.put("Employee:2:0", "ns1");
//        namespaces.put("Manager:1:0", "ns2");

        JAXBUnmarshaller jsonUnmarshaller = jaxbContext1.createUnmarshaller();
        jsonUnmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, "application/json");
        jsonUnmarshaller.setProperty(UnmarshallerProperties.JSON_NAMESPACE_PREFIX_MAPPER, namespaces);
        jsonUnmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, true);
        jsonUnmarshaller.setProperty(UnmarshallerProperties.JSON_ATTRIBUTE_PREFIX, "@");

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        xsdInputStream = new ByteArrayInputStream(xsd.getBytes(StandardCharsets.UTF_8));
        StreamSource xsdSource = new StreamSource(xsdInputStream);
        Schema schema = sf.newSchema(xsdSource);
        jsonUnmarshaller.setSchema(schema);

        StringReader stringReader = new StringReader(json);
        StreamSource jsonSource = new StreamSource(stringReader);
        JAXBElement<DynamicEntity> root = (JAXBElement) jsonUnmarshaller.unmarshal(jsonSource);

        JAXBMarshaller m = jaxbContext1.createMarshaller();
        m.setProperty(JAXBMarshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(root, System.out);

        return null;
    }
}
