
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ArticleRepository;
import domain.Article;
import domain.FollowUp;
import domain.User;

@Service
@Transactional
public class ArticleService {

	// Managed repository -----------------------------------------------------

	@Autowired
	ArticleRepository	articleRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	NewspaperService	newspaperService;

	@Autowired
	UserService			userService;

	@Autowired
	AdminService		adminService;

	//Importar la que pertenece a Spring
	@Autowired
	private Validator	validator;


	// Constructors -----------------------------------------------------------

	public ArticleService() {

	}

	// Simple CRUD methods ----------------------------------------------------

	//CREATE
	public Article create() {
		Article result;
		User userPrincipal;
		final Collection<FollowUp> followUps;

		result = new Article();
		userPrincipal = this.userService.findByPrincipal();
		result.setWriter(userPrincipal);
		followUps = new ArrayList<FollowUp>();
		result.setFollowUps(followUps);
		result.setDraftMode(true);

		return result;
	}

	//SAVE
	public Article save(final Article article) {
		Article result;

		Assert.notNull(article);
		Assert.isTrue(article.getWriter().equals(this.userService.findByPrincipal()));
		//Cuando no este en modo borrador tiene que tener asignado un periodico
		if (!article.isDraftMode())
			Assert.notNull(article.getNewspaper(), "tiene que asignarse un periodico para poder guardar en modo final");

		result = this.articleRepository.save(article);

		return result;
	}

	//DELETE
	public void delete(final Article article) {

		Assert.notNull(article);
		Assert.notNull(this.adminService.findByPrincipal());

		this.articleRepository.delete(article);
	}

	public Collection<Article> findAll() {

		Collection<Article> result;

		result = this.articleRepository.findAll();

		return result;

	}

	public Article findOne(final int articleId) {

		Assert.isTrue(articleId != 0);
		Article result;
		result = this.articleRepository.findOne(articleId);

		return result;

	}

	public void flush() {
		this.articleRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public Article reconstruct(final Article article, final BindingResult bindingResult) {
		Article result;
		Article articleBD;

		Assert.isTrue(article.getWriter().equals(this.userService.findByPrincipal()));
		if (article.getId() == 0) {
			User userPrincipal;
			final Collection<FollowUp> followUps;

			userPrincipal = this.userService.findByPrincipal();
			article.setWriter(userPrincipal);
			followUps = new ArrayList<FollowUp>();
			article.setFollowUps(followUps);
			article.setDraftMode(true);

			result = article;
		} else {
			articleBD = this.articleRepository.findOne(article.getId());
			Assert.isTrue(articleBD.getWriter().equals(article.getWriter()));
			article.setId(articleBD.getId());
			article.setVersion(articleBD.getVersion());

			result = article;
		}
		this.validator.validate(result, bindingResult);
		return result;
	}

}
