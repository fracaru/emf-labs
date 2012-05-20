/**
 * 
 */
package org.example.emf.standalone.test;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.example.emf.standalone.utils.ResourceUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.galaxy.process.model.collaborativemdeprocess.CollaborativemdeprocessFactory;
import fr.galaxy.process.model.collaborativemdeprocess.CollaborativemdeprocessPackage;
import fr.galaxy.process.model.collaborativemdeprocess.MDEProcess;
import fr.galaxy.process.model.collaborativemdeprocess.Participant;
import fr.galaxy.process.model.collaborativemdeprocess.Project;

/**
 * @author racaru
 * 
 */
public class TestStandaloneEMF {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// register
		System.out.println("Register factory ...");

		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
				Resource.Factory.Registry.DEFAULT_EXTENSION,
				new XMIResourceFactoryImpl());
	
		System.out.println("Register packages ...");
		//ProcessChangePackage.eINSTANCE.eClass();
		CollaborativemdeprocessPackage.eINSTANCE.eClass();
		//ChangePackage.eINSTANCE.eClass();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void TestCreate() throws IOException {
		EObject process = createModel();
		ResourceUtils.saveObject(process, new File("process.xmi"));
	}

	@Test
	public void TestLoad() throws IOException {
		EObject process = ResourceUtils.loadObject("process.xmi");
		System.out.println(ResourceUtils.serialize(process));
		Assert.assertNotNull(process);
	}

	private EObject createModel() {
		MDEProcess process = CollaborativemdeprocessFactory.eINSTANCE
				.createMDEProcess();

		Project project = CollaborativemdeprocessFactory.eINSTANCE
				.createProject();
		project.setId(1);
		project.setName("MyProject");
		project.setDescription("Super project");

		Participant john = CollaborativemdeprocessFactory.eINSTANCE
				.createParticipant();
		john.setId(1);
		john.setEmail("john.doe@hotmale.com");
		john.setFirstName("John");
		john.setLastName("DOE");

		Participant jean = CollaborativemdeprocessFactory.eINSTANCE
				.createParticipant();
		jean.setId(2);
		jean.setEmail("jean.DUPONT@hotmale.com");
		jean.setFirstName("Jean");
		jean.setLastName("DUPONT");

		project.getParticipants().add(jean);
		project.getParticipants().add(john);
		process.setProject(project);
		return process;
	}

}
