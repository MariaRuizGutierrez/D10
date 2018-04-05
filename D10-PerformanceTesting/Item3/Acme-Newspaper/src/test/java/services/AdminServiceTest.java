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
	
	//Login 
	@Test
	public void driveLoginAdmin() {

		final Object testingData[][] = {
			//Admin is registered
			{
				"admin", null
			},
			//Admin isn't registered
			{
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
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
	}
		
	//Test to edit all administrator attributes
	@Test
	public void driveEditNameAdministrator() {

		final Object testingData[][] = {
			//Try entering all the data of an admin with the null address and null telephone, positive case 
			{
				"admin", "admin", "adminTest", "surnameTest", null, null, "prueba@gmail.com", null
			}, 
			//Try entering a null phone number, this case positive
			{
				"admin", "admin", "adminTest", "surnameTest", "c/test", null, "prueba@gmail.com", null
			}, 
			//Try entering all the data of an admin, positive case
			{
				"admin", "admin", "adminTest", "surnameTest", "c/test", "+34657896576", "prueba@gmail.com", null
			}, 
			//Try entering a blank name, negative case
			{
				"admin", "admin", "", "surnameTest", null, null, "prueba@gmail.com", javax.validation.ConstraintViolationException.class
			}, 
			//Try entering a null name, negative case
			{
				"admin", "admin", null, "surnameTest", null, null, "prueba@gmail.com", javax.validation.ConstraintViolationException.class
			}, 
			//Try entering a blank surname, negative case
			{
				"admin", "admin", "adminTest", "", null, null, "prueba@gmail.com", javax.validation.ConstraintViolationException.class
			}, 
			//Try entering a null surname, negative case
			{
				"admin", "admin", "adminTest", null, null, null, "prueba@gmail.com", javax.validation.ConstraintViolationException.class
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
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}
		

}
