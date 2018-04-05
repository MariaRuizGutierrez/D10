
package services;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.UserAccount;
import utilities.AbstractTest;
import domain.Newspaper;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class UserServiceTest extends AbstractTest {

	// Supporting services ----------------------------------------------------
	@Autowired
	private UserService			userService;
	@Autowired
	private NewspaperService	newspaperService;

	@PersistenceContext
	EntityManager				entityManager;


	@Test
	public void driverLoginUser() {

		final Object testingData[][] = {
			{
				//login usuario registrado
				"user1", null

			}, {
				//login user no registrado
				"juanya", IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateLogin((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	private void templateLogin(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();

		}

		this.checkExceptions(expected, caught);

	}
	//Test Create-------------------------------------------------
	// Caso de uso 4.1:Register to the system as a user
	@Test
	public void driverCreateAndSave() {

		final Object testingData[][] = {
			{
				//Registrar user correctamente
				"usertest1", "passwordtest1", "miguel", "ternero", "calle Huertas nº 3", "662657322", "Email@email.com", null
			}, {
				//Registrar user con name en blanco
				"usertest2", "passwordtest2", "", "ternero", "calle Huertas nº 3", "662657322", "Email@email.com", javax.validation.ConstraintViolationException.class
			}, {
				//Registrar user con surname en blanco
				"usertest3", "passwordtest3", "miguel", "", "calle Huertas nº 3", "662657322", "Email@email.com", javax.validation.ConstraintViolationException.class
			}, {
				//Registrar user con un email no válido
				"usertest4", "passwordtest4", "miguel", "ternero", "calle Huertas nº 3", "662657322", "Email", javax.validation.ConstraintViolationException.class
			}, {
				//Registrar user ya registrado
				"usertest4", "passwordtest4", "miguel", "ternero", "calle Huertas nº 3", "662657322", "Email@gmail.com", org.springframework.dao.DataIntegrityViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Class<?>) testingData[i][7]);
	}
	private void templateCreateAndSave(final String username, final String password, final String name, final String surname, final String postalAdress, final String phone, final String email, final Class<?> expected) {
		Class<?> caught;
		User user;
		UserAccount userAccount;

		caught = null;
		try {
			user = this.userService.create();
			user.setName(name);
			user.setSurname(surname);
			user.setPostalAdress(postalAdress);
			user.setPhone(phone);
			user.setEmail(email);
			userAccount = user.getUserAccount();
			userAccount.setUsername(username);
			userAccount.setPassword(password);
			user.setUserAccount(userAccount);
			user = this.userService.save(user);
			this.userService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();

		}

		this.checkExceptions(expected, caught);

	}

	@Test
	public void driverEditUser() {

		final Object testingData[][] = {
			{
				//Edito mi nombre
				"user1", "user1 name edited", "surname user 1", "postal Adress user 1", "617557203", "user1@acmenewspaper.com", null
			}, {
				//Edito mis apellidos
				"user1", "user 1", "surname user 1 edited", "postal Adress user 1", "617557203", "user1@acmenewspaper.com", null
			}, {
				//Edito mi dirección
				"user1", "user 1", "surname user 1", "postal Adress user 1 edited", "617557203", "user1@acmenewspaper.com", null
			}, {
				//Edito mi email
				"user1", "user 1", "surname user 1", "postal Adress user 1", "617557203", "user1EDITED@acmenewspaper.com", null
			}, {
				//Edito mi nombre por uno en blanco
				"user1", "", "surname user 1", "postal Adress user 1", "617557203", "user1@acmenewspaper.com", ConstraintViolationException.class
			}, {
				//Edito mis apellidos por uno en blanco
				"user1", "user 1", "", "postal Adress user 1", "617557203", "user1@acmenewspaper.com", ConstraintViolationException.class
			}, {
				//Edito mi email por uno que no cumple el formato
				"user1", "user 1", "surname user 1", "postal Adress user 1", "617557203", "user1", ConstraintViolationException.class
			}, {
				//Edito mi email por uno en blanco
				"user1", "", "surname user 1", "postal Adress user 1", "617557203", "", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEditUser(super.getEntityId((String) testingData[i][0]), (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
	}
	private void templateEditUser(final int userNameId, final String name, final String surname, final String postalAdress, final String phone, final String email, final Class<?> expected) {
		Class<?> caught;
		User user;
		user = this.userService.findOne(userNameId);

		caught = null;
		try {
			super.authenticate(user.getUserAccount().getUsername());
			user.setName(name);
			user.setSurname(surname);
			user.setPostalAdress(postalAdress);
			user.setPhone(phone);
			user.setEmail(email);
			user = this.userService.save(user);
			this.unauthenticate();
			this.userService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();

		}

		this.checkExceptions(expected, caught);
	}

	// Test listAssist ------------------------------------------------------
	// Se comprueba el listar las Rendezvouses para poder asistir
	//Caso de uso 55: List the rendezvouses that he or shes RSVPd parte 1
	@Test
	public void driverLisNewspapers() {
		final Object testingData[][] = {
			{
				//El user 1 lista los newspapers publicados y aparece el newspaper5 porque ha sido publicado y está público
				"user1", "newspaper5", null
			}, {
				//El user 1 lista los newspapers publicados y NO le aparece el newspaper2 porque NO ha sido publicado
				"user1", "newspaper2", IllegalArgumentException.class
			}, {
				//El user 1 lista los newspapers publicados y NO le aparece el newspaper1 porque NO ha es público aunque SÍ ha sido públicado.
				"user1", "newspaper1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListNewspapers(super.getEntityId((String) testingData[i][0]), super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	private void templateListNewspapers(final int usernameId, final int newspaperId, final Class<?> expected) {
		Class<?> caught;
		User user;
		Collection<Newspaper> newspapers;
		final Newspaper newspaper;

		user = this.userService.findOne(usernameId);
		newspaper = this.newspaperService.findOne(newspaperId);
		caught = null;
		try {
			super.authenticate(user.getUserAccount().getUsername());
			newspapers = this.newspaperService.findNewspapersPublishedAndOpen();
			Assert.isTrue(newspapers.contains(newspaper));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
