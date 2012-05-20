package org.example.emf.standalone.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

public class ResourceUtils {

	/**
	 * Serialize the EObject in XMI and return as String.
	 * 
	 * @param object
	 *            the object to serialize
	 * @return the String representation of serialized EObject
	 * @throws IOException
	 */
	public static String serialize(EObject object) throws IOException {
		// Store resource
		StringWriter out = new StringWriter();
		serialize(object, out);
		return out.toString();
	}

	/**
	 * Serialize the EObject in the given Writer.
	 * @param object
	 * @param out
	 * @throws IOException
	 */
	public static void serialize(EObject object, Writer out) throws IOException {
		XMIResource resource = null;
		if (object.eResource() != null) {
			resource = (XMIResource) object.eResource();
		} else {
			resource = new XMIResourceImpl();
			resource.getContents().add(object);
		}
		// Store resource
		saveResource(resource, out);
	}

	/**
	 * Load a serialized EObject
	 * @param file
	 * @return
	 */
	public static EObject loadObject(String file) {
		return loadObject(new File(file));
	}

	/**
	 * Load a serialized EObject
	 * @param file
	 * @return
	 */
	public static EObject loadObject(File file) {
		URI uri = URI.createFileURI(file.getAbsolutePath());
		return loadObject(uri);
	}

	/**
	 * Load a serialized EObject
	 * @param file
	 * @return
	 */
	public static EObject loadObject(URI uri) {
		ResourceSet rs = new ResourceSetImpl();
		Resource resource = rs.getResource(uri, true);
		return resource.getContents().get(0);
	}

	/**
	 * Save an EMF resource
	 * @param resource
	 * @throws IOException
	 */
	public static void saveResource(Resource resource) throws IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(XMIResource.OPTION_SKIP_ESCAPE_URI, Boolean.FALSE);
		map.put(XMIResource.OPTION_ENCODING, "UTF-8");
		resource.save(map);

	}

	/**
	 * Save an EMF resource
	 * @param resource
	 * @param out
	 * @throws IOException
	 */
	public static void saveResource(XMIResource resource, Writer out)
			throws IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(XMIResource.OPTION_SKIP_ESCAPE_URI, Boolean.FALSE);
		map.put(XMIResource.OPTION_ENCODING, "UTF-8");
		resource.save(out, map);
	}

	/**
	 * Save an EMF resource
	 * @param object
	 * @param file
	 * @throws IOException
	 */
	public static void saveObject(EObject object, String file)
			throws IOException {
		saveObject(object, new File(file));
	}

	/**
	 * Save an EObject in a file
	 * @param object
	 * @param file
	 * @throws IOException
	 */
	public static void saveObject(EObject object, File file) throws IOException {
		ResourceSet rs = new ResourceSetImpl();
		URI uri = URI.createFileURI(file.getAbsolutePath());
		Resource resource = rs.createResource(uri);
		resource.getContents().add(object);
		saveResource(resource);
	}

}
