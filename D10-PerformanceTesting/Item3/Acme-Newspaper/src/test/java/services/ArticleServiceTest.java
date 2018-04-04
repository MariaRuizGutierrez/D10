
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Article;
import domain.Newspaper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ArticleServiceTest extends AbstractTest {

	// Supporting services ----------------------------------------------------

	@Autowired
	NewspaperService	newspaperService;

	@Autowired
	UserService			userService;

	@Autowired
	AdminService		adminService;

	@Autowired
	ArticleService		articleService;

	@PersistenceContext
	EntityManager		entityManager;


	//Caso de uso 6.3: Write an article and attach it to any newspaper that has not been published, yet. Note that articles may be saved in draft mode, which allows to modify them later, or final model, which freezes them forever.
	@SuppressWarnings("unchecked")
	@Test
	public void driverListAndEdit() {
		final Collection<String> picturesOk;
		final Collection<String> picturesBadUrls;

		picturesOk = this.addPicturesOk();
		picturesBadUrls = this.addPicturesBadUrls();

		final Object testingData[][] = {

			{
				//Se edita el article4 para el newspaper3 (publico y no publicado) correctamente
				"user3", "article4", "newspaper3", "title test", null, "body test", picturesOk, true, true, null
			}, {
				//Se edita el article4 para el newspaper3 (publico y no publicado) incorrectamente porque el user2 no es el creador del article4
				"user2", "article4", "newspaper3", "title test", null, "body test", picturesOk, true, true, IllegalArgumentException.class
			}, {
				//Se edita el article4 para el newspaper3 (publico y no publicado) incorrectamente porque tiene el title en blank
				"user3", "article4", "newspaper3", "", null, "body test", picturesOk, true, true, javax.validation.ConstraintViolationException.class
			}, {
				//Se edita el article4 para el newspaper3 (publico y no publicado) incorrectamente porque tiene el body en blank
				"user3", "article4", "newspaper3", "title test", null, "", picturesOk, true, true, javax.validation.ConstraintViolationException.class
			}, {
				//Se edita el article4 para el newspaper3 (publico y no publicado) correctamente con la lista de pictures vacia
				"user3", "article4", "newspaper3", "title test", null, "body test", new ArrayList<String>(), true, true, null
			}, {
				//Se edita el article4 para el newspaper3 (publico y no publicado) incorrectamente con la lista de pictures con mala urls
				//TODO Corregir el modelo java para que el fallo sea javax.validation.ConstraintViolationException.class
				"user3", "article4", "newspaper3", "title test", null, "body test", picturesBadUrls, true, true, null
			}, {
				//Se edita el article4 para el newspaper3 (publico y no publicado) correctamente poniendo el article en modo final
				"user3", "article4", "newspaper3", "title test", null, "body test", picturesOk, false, true, null
			}, {
				//Se edita el article4 para el newspaper1 (publico y no publicado) incorrectamente porque el newspaper1 ya esta publicado (comprobamos el list)
				"user3", "article4", "newspaper1", "title test", null, "body test", picturesOk, true, true, IllegalArgumentException.class
			}, {
				//Se edita el article1 para el newspaper1 (privado y publicado) incorrectamente porque ya esta publicado el newspaper
				"user1", "article1", "newspaper1", "title test", null, "body test", picturesOk, true, false, IllegalArgumentException.class
			}, {
				//Se edita el article4 para el newspaper3 (publico y no publicado) correctamente poniendo el newspaper en null con el draftMode en true
				"user3", "article4", null, "title test", null, "body test", picturesOk, true, false, null
			}, {
				//Se edita el article4 para el newspaper3 (publico y no publicado) incorrectamente poniendo el newspaper en null con el draftMode en false
				"user3", "article4", null, "title test", null, "body test", picturesOk, false, false, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListAndEdit((String) testingData[i][0], (Integer) super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5],
				(Collection<String>) testingData[i][6], (boolean) testingData[i][7], (boolean) testingData[i][8], (Class<?>) testingData[i][9]);
	}
	private void templateListAndEdit(final String username, final Integer articleId, final String newspaperBean, final String title, final String publishedMoment, final String body, final Collection<String> pictures, final boolean draftMode,
		final boolean checkList, final Class<?> expected) {
		Article article;
		Newspaper newspaper;
		Collection<Newspaper> newspapersNotPublished;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			article = this.articleService.findOne(articleId);
			if (newspaperBean != null) {
				newspaper = this.newspaperService.findOne(super.getEntityId(newspaperBean));
				article.setNewspaper(newspaper);
				if (checkList) {
					newspapersNotPublished = this.newspaperService.findNewspaperNotPublished();
					//Se comprueba que el newspaper pasado por parametro se encuentra en la lista de newspapers no publicados aun
					Assert.isTrue(newspapersNotPublished.contains(newspaper), "el newspaper no se encuentra entre los no publicados");
				}
			} else
				article.setNewspaper(null);

			article.setTitle(title);
			article.setBody(body);
			article.setPictures(pictures);
			article.setDraftMode(draftMode);
			article = this.articleService.save(article);
			this.articleService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

		super.unauthenticate();
	}

	//Caso de uso 6.3: Write an article and attach it to any newspaper that has not been published, yet. Note that articles may be saved in draft mode, which allows to modify them later, or final model, which freezes them forever. (parte 2)
	@SuppressWarnings("unchecked")
	@Test
	public void driverCreateAndSave() {
		final Collection<String> picturesOk;

		picturesOk = this.addPicturesOk();

		final Object testingData[][] = {

			{
				//Se crea un article para el newspaper3 (publico y no publicado) correctamente
				"user3", "newspaper3", "title test", null, "body test", picturesOk, true, null
			}, {
				//Se crea un article para el newspaper1 (privado y publicado) incorrectamente porque ya esta publicado
				"user3", "newspaper1", "title test", null, "body test", picturesOk, true, IllegalArgumentException.class
			}, {
				//Se crea un article para el newspaper1 (privado y publicado) incorrectamente porque solo lo puede crear el user
				"customer1", "newspaper1", "title test", null, "body test", picturesOk, true, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Collection<String>) testingData[i][5], (boolean) testingData[i][6],
				(Class<?>) testingData[i][7]);
	}
	private void templateCreateAndSave(final String username, final String newspaperBean, final String title, final String publishedMoment, final String body, final Collection<String> pictures, final boolean draftMode, final Class<?> expected) {
		Article article;
		Newspaper newspaper;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			article = this.articleService.create();
			if (newspaperBean != null) {
				newspaper = this.newspaperService.findOne(super.getEntityId(newspaperBean));
				article.setNewspaper(newspaper);
			} else
				article.setNewspaper(null);

			article.setTitle(title);
			article.setBody(body);
			article.setPictures(pictures);
			article.setDraftMode(draftMode);
			article = this.articleService.save(article);
			this.articleService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

		super.unauthenticate();
	}

	//Other methods --------------------------------------------------

	private Collection<String> addPicturesOk() {
		Collection<String> picturesOk;
		picturesOk = new ArrayList<String>();
		picturesOk.add("http://www.picture1.com");
		picturesOk.add("http://www.picture2.com");
		return picturesOk;
	}
	private Collection<String> addPicturesBadUrls() {
		Collection<String> picturesBadUrls;
		picturesBadUrls = new ArrayList<String>();
		picturesBadUrls.add("http://www.picture1.com");
		picturesBadUrls.add("esto no es una url");
		return picturesBadUrls;
	}

}
