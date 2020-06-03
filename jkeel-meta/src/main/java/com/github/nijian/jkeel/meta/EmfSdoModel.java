package com.github.nijian.jkeel.meta;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.xsd.ecore.XSDEcoreBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class EmfSdoModel {

    private static Logger logger = LoggerFactory.getLogger(EmfSdoModel.class);

    private ResourceSet loadedModelResources = null;

    /**
     * Load EMF/SDO model from XSDs and set the this.loadedModelResources ResourceSet with the EPackages found.
     */
    public void initModelFromXsd() {

        final Collection<Object> loadedPackagesEtc = new XSDEcoreBuilder().generate(getSchemaUris());

        final Collection<EPackage> eCorePackages = new LinkedList<>();
        for (Object loadedObject : loadedPackagesEtc) {
            if (loadedObject instanceof EPackage) {
                eCorePackages.add((EPackage) loadedObject);
            } else {
                final String typeInfo = (loadedObject == null) ?
                        "N/A" : loadedObject.getClass().getName();
                logger.info("initModelFromXsd: A non-EPackage in the input: " + typeInfo);
            }
        }
        // TODO Fail if no packages found
        this.loadedModelResources = registerDynamicPackages(eCorePackages);
    }

    private Collection<URI> getSchemaUris() {
        final Collection<URI> result = new LinkedList<>();

        final URL xsdUrl = getClass().getResource("/schema.xsd"); // fail if null
        result.add(URI.createURI(xsdUrl.toExternalForm()));

        return result;
    }

    private ResourceSet registerDynamicPackages(
            final Collection<EPackage> eCorePackages) {
        final ResourceSet resourceSet = new ResourceSetImpl();

        // This is necessary when running standalone for no factories have been registered yet:
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xml",
                new XMLResourceFactoryImpl());

        for (EPackage ePackage : eCorePackages) {
            resourceSet.getPackageRegistry().put(ePackage.getNsURI(), ePackage);
            // or register globally: EPackage.Registry.INSTANCE.put(ePackage.getNsURI(), ePackage);

            // Create SDO's EDataObjects or EMF's EObjects or st. else?
//            ePackage.setEFactoryInstance(createModelObjectFactory());
        }
        return resourceSet;
    }

    public EObject loadFromXml(final InputStream xmlStream) throws IOException {
        final Resource resource = loadedModelResources.createResource(
                URI.createURI("/testservices.xml")); // fake URI

        resource.load(xmlStream, createXmlResourceDeSerializationOptions());
        // May throw org.eclipse.emf.ecore.resource.Resource$IOWrappedException: Class 'myRootElement' not found.
        // <= ecore.xmi.ClassNotFoundException: Class 'myRootElement' not found.
        // if no EClass found for the root XML element given its name and namespace

        logger.info("Resource loaded:" + resource + ", contents:" + resource.getContents());
        // => [DynamicEObjectImpl (eClass: EClassImpl(name: myRootElement) (instanceClassName: null) (abstract: false, interface: false))]

        final EObject loadedEObject = resource.getContents().get(0);
        return loadedEObject;
    }

    private Map<?, ?> createXmlResourceDeSerializationOptions() {
        return new HashMap<>();
    }


    public final static void main(String[] args) {
        EmfSdoModel x = new EmfSdoModel();
        x.initModelFromXsd();

        try {
            x.loadFromXml(null);
        } catch (Exception e) {

        }
    }
}
