
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FollowUpRepository;
import domain.Article;
import domain.FollowUp;
import domain.User;

@Service
@Transactional
public class FollowUpService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private FollowUpRepository	followUpRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private UserService			userService;


	// Constructors -----------------------------------------------------------
	public FollowUpService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public FollowUp create(final Article article) {
		Date publicationMoment;
		FollowUp followUp;
		User userConnected;

		//Usuario conectado
		userConnected = this.userService.findByPrincipal();
		//Restricción no poder crear una followUp para un articulo en modo borrador
		Assert.isTrue(!article.isDraftMode());
		//Restricción no poder crear una followUp para un newspaper que no ha sido publicado
		Assert.isTrue(article.getNewspaper().getPublicationDate() != null);
		//Restricción no poder crear una followUp para un artículo que no sea del user conectado
		Assert.isTrue(article.getWriter().equals(userConnected));

		publicationMoment = new Date(System.currentTimeMillis() - 1000);

		//Comprobamos que sea un usuario
		this.userService.checkPrincipal();

		followUp = new FollowUp();
		followUp.setPublicationMoment(publicationMoment);
		followUp.setArticle(article);

		return followUp;
	}

	public FollowUp save(final FollowUp followUp) {
		Assert.notNull(followUp);
		FollowUp result;
		Date publicationMoment;

		publicationMoment = new Date(System.currentTimeMillis() - 1000);

		result = this.followUpRepository.save(followUp);
		result.setPublicationMoment(publicationMoment);

		Assert.notNull(result);

		return result;

	}

	public void delete(final FollowUp followUp) {
		
		Assert.notNull(followUp);
		Assert.isTrue(followUp.getId() != 0);
		
		this.followUpRepository.delete(followUp);

	}

	public FollowUp findOne(final int followUpId) {
		Assert.isTrue(followUpId != 0);
		FollowUp result;

		result = this.followUpRepository.findOne(followUpId);
		return result;
	}

	public Collection<FollowUp> findAll() {
		Collection<FollowUp> result;

		result = this.followUpRepository.findAll();
		return result;
	}

	// Other business methods -------------------------------------------------
	public void flush() {
		this.followUpRepository.flush();
	}
}
