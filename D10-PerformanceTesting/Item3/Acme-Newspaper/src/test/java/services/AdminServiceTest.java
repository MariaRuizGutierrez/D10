package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import domain.Admin;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdminServiceTest extends AbstractTest{
	
	// Supporting services ----------------------------------------------------

	@Autowired
	private AdminService	adminService;
		
	@PersistenceContext
	EntityManager					entityManager;
	
	//Test para comprobar que un administrador se loguea correctamente.
	@Test
	public void driveLoginAdmin() {

		final Object testingData[][] = {
			//admin está registrado
			{
				"admin", null
			}, {
				"adminNoRegistrado", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateLoginAdmin((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void templateLoginAdmin(final String username, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
	}
		
	//Test que edita todos los atributos de administrador
	@Test
	public void driveEditNameAdministrator() {

		final Object testingData[][] = {
			//admin está registrado
			{
				"admin", "admin", "adminTest", "surnameTest", null, null, "prueba@gmail.com", null
			}, {
				"admin", "admin", "adminTest", "surnameTest", "c/test", null, "prueba@gmail.com", null
			}, {
				"admin", "admin", "adminTest", "surnameTest", "c/test", "+34657896576", "prueba@gmail.com", null
			}, {
				"admin", "admin", "", "surnameTest", null, null, "prueba@gmail.com", javax.validation.ConstraintViolationException.class
			}, {
				"admin", "admin", null, "surnameTest", null, null, "prueba@gmail.com", javax.validation.ConstraintViolationException.class
			}, {
				"admin", "admin", "adminTest", "surnameTest", null, null, "prueba@gmail.com", null
			}, {
				"admin", "admin", "adminTest", "", null, null, "prueba@gmail.com", javax.validation.ConstraintViolationException.class
			}, {
				"admin", "admin", "adminTest", null, null, null, "prueba@gmail.com", javax.validation.ConstraintViolationException.class
			}, {
				"admin", "admin", "adminTest", null, null, null, "pruebagmail.com", javax.validation.ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditAdministrator((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Class<?>) testingData[i][7]);

	}

	public void templateEditAdministrator(final String entity, final String username, final String name, final String surname, final String postalAddress, final String phone, final String email, final Class<?> expected) {

		Class<?> caught;
		Admin admin;

		caught = null;
		admin = this.adminService.findOne(super.getEntityId(entity));

		try {
			super.authenticate(username);
			admin.setName(name);
			admin.setSurname(surname);
			admin.setPostalAdress(postalAddress);
			admin.setPhone(phone);
			admin.setEmail(email);
			this.adminService.save(admin);
			this.unauthenticate();
			this.adminService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}
		

}
