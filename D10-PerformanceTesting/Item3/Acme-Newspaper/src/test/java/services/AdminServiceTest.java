
package services;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Admin;
import domain.Article;
import domain.Chirp;
import domain.Newspaper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdminServiceTest extends AbstractTest {

	// Supporting services ----------------------------------------------------

	@Autowired
	private AdminService	adminService;
	
	@Autowired
	private ArticleService 	articleService;
	
	@Autowired
	private NewspaperService newspaperService;
	
	@Autowired
	private ChirpService chirpService;

	@PersistenceContext
	EntityManager			entityManager;


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
			admin.setPostalAddress(postalAddress);
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
	
	//Use case 7.1. An admin removes an article that he or she thinks is inappropriate.
	@Test
	public void driveRemoveArticleAdmin() {

		final Object testingData[][] = {
			//Admin remove article, positive case
			{
				"admin", "article2", null
			},
			
			//Admin can't remove article 1 because his newspaper have subscriptions. Negative case
			{
				"admin", "article1", java.lang.IllegalArgumentException.class
			},
			//User can't remove an article. Negative case
			{
				"user1", "article2", java.lang.IllegalArgumentException.class
			},
			//Customer can't remove an article. Negative case
			{
				"customer1", "article2", java.lang.IllegalArgumentException.class
			}
			
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateRemoveArticleAdmin((String) testingData[i][0], (String) testingData[i][1], 
				(Class<?>) testingData[i][2]);

	}
	

	public void templateRemoveArticleAdmin(final String username, final String articleEntity, final Class<?> expected) {

		Class<?> caught;
		Article article;

		caught = null;
		article = this.articleService.findOne(super.getEntityId(articleEntity));
		

		try {
			super.authenticate(username);
			this.articleService.delete(article);
			this.unauthenticate();
			this.articleService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}
	
	//Use case 7.2. An admin removes a newspaper that he or she thinks is inappropriate.
	@Test
	public void driveRemoveNewspaperAdmin() {

		final Object testingData[][] = {
			//Admin remove newspaper, positive case
			{
				"admin", "newspaper2", null
			},
			
			//Admin can't remove newspaper 1 because it have subscriptions. Negative case
			{
				"admin", "newspaper1", java.lang.IllegalArgumentException.class
			},
			//User can't remove a newspaper. Negative case
			{
				"user1", "newspaper3", java.lang.IllegalArgumentException.class
			},
			//Customer can't remove a newspaper. Negative case
			{
				"customer1", "newspaper3", java.lang.IllegalArgumentException.class
			}
			
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateRemoveNewspaperAdmin((String) testingData[i][0], (String) testingData[i][1], 
				(Class<?>) testingData[i][2]);

	}
	

	public void templateRemoveNewspaperAdmin(final String username, final String newspaperEntity, final Class<?> expected) {

		Class<?> caught;
		Newspaper newspaper;

		caught = null;
		newspaper = this.newspaperService.findOne(super.getEntityId(newspaperEntity));
		

		try {
			super.authenticate(username);
			this.newspaperService.delete(newspaper);
			this.unauthenticate();
			this.newspaperService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}
	
	//Use case 7.2. An admin removes a chirp that he or she thinks is inappropriate.
	@Test
	public void driveRemoveChirpAdmin() {

		final Object testingData[][] = {
			//Admin remove chirp, positive case
			{
				"admin", "chirp2", null
			},
			
			//User can't remove a chirp. Negative case
			{
				"user1", "chirp1", java.lang.IllegalArgumentException.class
			},
			//Customer can't remove a chirp. Negative case
			{
				"customer1", "chirp1", java.lang.IllegalArgumentException.class
			}
			
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateRemoveChirpAdmin((String) testingData[i][0], (String) testingData[i][1], 
				(Class<?>) testingData[i][2]);

	}
	

	public void templateRemoveChirpAdmin(final String username, final String chirpEntity, final Class<?> expected) {

		Class<?> caught;
		Chirp chirp;

		caught = null;
		chirp = this.chirpService.findOne(super.getEntityId(chirpEntity));
		

		try {
			super.authenticate(username);
			this.chirpService.delete(chirp);
			this.unauthenticate();
			this.chirpService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}
	
	//Use case 17.2. An admin lists the articles that contains taboo words
	@Test
	public void driveListArticleWithTabooWordAdmin() {

		final Object testingData[][] = {
			//Admin lists articles, positive case
			{
				"admin", "article2", null
			},
			
			//User can't list articles. Negative case
			{
				"user1", "article2", java.lang.IllegalArgumentException.class
			},
			//Customer can't list an articles. Negative case
			{
				"customer1", "article2", java.lang.IllegalArgumentException.class
			}
			
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListArticleWithTabooWordAdmin((String) testingData[i][0], (String) testingData[i][1], 
				(Class<?>) testingData[i][2]);

	}
	

	public void templateListArticleWithTabooWordAdmin(final String username, final String articleEntity, final Class<?> expected) {

		Class<?> caught;
		Article article;
		Collection<Article> articles;
		
		caught = null;
		article = this.articleService.findOne(super.getEntityId(articleEntity));
		

		try {
			super.authenticate(username);
			articles = this.articleService.articleWithTabooWord();
			Assert.isTrue(articles.contains(article));
			this.unauthenticate();
			this.articleService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}
	
	//Use case 17.3. An admin lists the newspapers that contains taboo words
	@Test
	public void driveListNewspaperWithTabooWordAdmin() {

		final Object testingData[][] = {
			//Admin list newspapers, positive case
			{
				"admin", "newspaper1", null
			},
			
			//User can't list newspapers. Negative case
			{
				"user1", "newspaper1", java.lang.IllegalArgumentException.class
			},
			//Customer can't list newspapers. Negative case
			{
				"customer1", "newspaper1", java.lang.IllegalArgumentException.class
			}
			
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListNewspaperWithTabooWordAdmin((String) testingData[i][0], (String) testingData[i][1], 
				(Class<?>) testingData[i][2]);

	}
	

	public void templateListNewspaperWithTabooWordAdmin(final String username, final String newspaperEntity, final Class<?> expected) {

		Class<?> caught;
		Newspaper newspaper;
		Collection<Newspaper> newspapers;
		
		caught = null;
		newspaper = this.newspaperService.findOne(super.getEntityId(newspaperEntity));
		

		try {
			super.authenticate(username);
			newspapers = this.newspaperService.NewspaperWithTabooWord();
			Assert.isTrue(newspapers.contains(newspaper));
			this.unauthenticate();
			this.articleService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}
	
	//Use case 17.3. An admin lists the chirps that contains taboo words
	@Test
	public void driveListChirpsWithTabooWordAdmin() {

		final Object testingData[][] = {
			//Admin list newspapers, positive case
			{
				"admin", "chirp1", null
			},
			
			//User can't list newspapers. Negative case
			{
				"user1", "chirp1", java.lang.IllegalArgumentException.class
			},
			//Customer can't list newspapers. Negative case
			{
				"customer1", "chirp1", java.lang.IllegalArgumentException.class
			}
			
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListChirpsWithTabooWordAdmin((String) testingData[i][0], (String) testingData[i][1], 
				(Class<?>) testingData[i][2]);

	}
	

	public void templateListChirpsWithTabooWordAdmin(final String username, final String newspaperEntity, final Class<?> expected) {

		Class<?> caught;
		Chirp chirp;
		Collection<Chirp> chirps;
		
		caught = null;
		chirp = this.chirpService.findOne(super.getEntityId(newspaperEntity));
		

		try {
			super.authenticate(username);
			chirps = this.chirpService.ChirpWithTabooWord();
			Assert.isTrue(chirps.contains(chirp));
			this.unauthenticate();
			this.articleService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}
	
		

}
