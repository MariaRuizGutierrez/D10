
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.NewspaperRepository;
import domain.Article;
import domain.Newspaper;
import domain.User;

@Service
@Transactional
public class NewspaperService {

	// Managed repository -----------------------------------------------------

	@Autowired
	NewspaperRepository	newspaperRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	UserService			userService;

	@Autowired
	ArticleService		articleService;

	@Autowired
	AdminService		adminService;


	// Constructors -----------------------------------------------------------

	public NewspaperService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	//CREATE
	public Newspaper create() {
		final Newspaper result;
		User userPrincipal;
		final Collection<Article> articles;

		userPrincipal = this.userService.findByPrincipal();
		articles = new ArrayList<Article>();
		result = new Newspaper();
		result.setArticles(articles);
		result.setPublisher(userPrincipal);

		return result;
	}

	//SAVE
	public Newspaper save(final Newspaper newspaper) {
		Newspaper result;

		Assert.notNull(newspaper);
		Assert.isTrue(newspaper.getPublisher().equals(this.userService.findByPrincipal()));

		result = this.newspaperRepository.save(newspaper);

		return result;
	}

	//DELETE
	public void delete(final Newspaper newspaper) {
		Assert.notNull(newspaper);
		Assert.notNull(this.adminService.findByPrincipal());
		//Solo se pueden eliminar los newspaper publicos
		Assert.isTrue(newspaper.isOpen());

		this.newspaperRepository.delete(newspaper);
	}

	public Collection<Newspaper> findAll() {

		Collection<Newspaper> result;

		result = this.newspaperRepository.findAll();

		return result;

	}

	public Newspaper findOne(final int newspaperId) {

		Assert.isTrue(newspaperId != 0);
		Newspaper result;
		result = this.newspaperRepository.findOne(newspaperId);

		return result;

	}

	public void flush() {
		this.newspaperRepository.flush();
	}

	// Other business methods -------------------------------------------------

	//PUBLICAR
	public void publish(Newspaper newspaper) {

		Assert.isTrue(newspaper.getId() != 0);
		Assert.isTrue(newspaper.getPublisher().equals(this.userService.findByPrincipal()));
		Assert.isTrue(this.isAllFinalMode(newspaper.getId()), "todos sus articulos tienen que estar en modo final");
		Assert.isTrue(newspaper.getArticles().size() != 0, "tiene que tener al menos un articulo para publicarse");

		newspaper.setPublicationDate(new Date(System.currentTimeMillis() - 1000));
		newspaper = this.save(newspaper);
	}

	private boolean isAllFinalMode(final int newsPaperId) {
		Collection<Article> articlesWithoutFinalMode;
		boolean result;

		articlesWithoutFinalMode = this.newspaperRepository.isAllFinalMode(newsPaperId);
		result = true;
		if (articlesWithoutFinalMode.size() != 0)
			//Esto significa que existe al menos un articulo que esta en modo borrador
			result = false;
		return result;
	}

	public Newspaper findByArticleId(final int articleId) {
		Newspaper result;

		result = this.newspaperRepository.findByArticleId(articleId);

		return result;
	}

	public Collection<Newspaper> findNewspapersCreatedByUser() {
		Collection<Newspaper> result;
		User userPrincipal;

		userPrincipal = this.userService.findByPrincipal();
		result = this.newspaperRepository.findNewspapersCreatedByUser(userPrincipal.getId());
		return result;
	}

}
