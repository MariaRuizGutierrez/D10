
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.NewspaperRepository;
import domain.Article;
import domain.Newspaper;
import domain.Subscription;
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

	@Autowired
	SubscriptionService	subscriptionService;
	
	@Autowired
	TabooWordService tabooWordService;

	//Importar la que pertenece a Spring
	@Autowired
	private Validator	validator;


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

		Collection<Subscription> subscriptions;

		subscriptions = this.subscriptionService.findSubscriptionByNewspaper(newspaper.getId());

		Assert.notNull(newspaper);
		Assert.notNull(this.adminService.findByPrincipal());
		//Solo se pueden eliminar los newspaper publicos
		Assert.isTrue(newspaper.isOpen() || (newspaper.isOpen() == false && subscriptions.size() == 0), "Se pueden eliminar los periodicos publicos y privado que a�n no tengas suscripciones");

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

	public Collection<Newspaper> findNewspapersByKeyword(final String keyWord) {
		Collection<Newspaper> result;
		result = this.newspaperRepository.findNewspapersByKeyword(keyWord);
		return result;
	}
	public Collection<Newspaper> findNewspapersByKeywordAuthenticate(final String keyWord) {
		Collection<Newspaper> result;
		result = this.newspaperRepository.findNewspapersByKeywordAuthenticate(keyWord);
		return result;
	}

	public void flush() {
		this.newspaperRepository.flush();
	}

	// Other business methods -------------------------------------------------

	//PUBLICAR
	public void publish(Newspaper newspaper) {

		Assert.isTrue(newspaper.getId() != 0);
		Assert.isTrue(newspaper.getPublisher().equals(this.userService.findByPrincipal()), "el publicador del periodico debe ser el mismo que el logueado");
		Assert.isTrue(this.isAllFinalMode(newspaper.getId()), "todos sus articulos tienen que estar en modo final");
		Assert.isTrue(newspaper.getArticles().size() != 0, "tiene que tener al menos un articulo para publicarse");
		Assert.isNull(newspaper.getPublicationDate(), "la fecha de publicacion tiene que estar vacia");

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

	public Collection<Newspaper> findNewspapersPublishedAndOpen() {
		Collection<Newspaper> result;

		result = this.newspaperRepository.findNewspapersPublishedAndOpen();

		return result;
	}

	public Collection<Newspaper> findNewspaperNotPublished() {
		Collection<Newspaper> result;

		result = this.newspaperRepository.findNewspaperNotPublished();

		return result;
	}

	public Collection<Newspaper> findNewspapersPublished() {
		Collection<Newspaper> result;

		result = this.newspaperRepository.findNewspapersPublished();

		return result;
	}

	public Collection<Newspaper> findByUserId(final int userId) {
		Collection<Newspaper> result;

		result = this.newspaperRepository.findByUserId(userId);

		return result;
	}

	public Collection<Newspaper> findPrivateAndPublicatedNewspapersToSubscribe() {
		Collection<Newspaper> result;

		result = this.newspaperRepository.findPrivateAndPublicatedNewspapersToSubscribe();

		return result;
	}

	public Collection<Newspaper> findNewspapersSubscribedByCustomerId(final int customerId) {
		Collection<Newspaper> result;

		result = this.newspaperRepository.findNewspapersSubscribedByCustomerId(customerId);

		return result;
	}

	public Newspaper reconstruct(final Newspaper newspaper, final BindingResult bindingResult) {
		Newspaper result;
		Newspaper newspaperBD;
		if (newspaper.getId() == 0) {
			User userPrincipal;
			final Collection<Article> articles;

			userPrincipal = this.userService.findByPrincipal();
			articles = new ArrayList<Article>();
			newspaper.setArticles(articles);
			newspaper.setPublisher(userPrincipal);
			result = newspaper;
		} else {
			newspaperBD = this.newspaperRepository.findOne(newspaper.getId());
			newspaper.setId(newspaperBD.getId());
			newspaper.setVersion(newspaperBD.getVersion());
			newspaper.setPublicationDate(newspaperBD.getPublicationDate());
			newspaper.setPublisher(newspaperBD.getPublisher());
			if (newspaper.getArticles() == null)
				newspaper.setArticles(new ArrayList<Article>());
			else
				newspaper.setArticles(newspaperBD.getArticles());
			result = newspaper;
		}
		this.validator.validate(result, bindingResult);
		return result;
	}
	
	public Collection<Newspaper> findNewspaperWithTabooWord(String tabooWord){
		
		Collection<Newspaper> result;
		
		result = this.newspaperRepository.findNewspaperWithTabooWord(tabooWord);
		
		return result;
	}
	
	public Collection<Newspaper> NewspaperWithTabooWord(){
		
		Collection<Newspaper> result;
		Collection<String> tabooWords;
		Iterator<String> it;
		
		result = new ArrayList<>();
		tabooWords = this.tabooWordService.findTabooWordByName();
		it = tabooWords.iterator();
		while(it.hasNext())
			result.addAll(this.findNewspaperWithTabooWord(it.next()));
		
		return result;
		
	}

}
