
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FollowUpRepository;
import domain.FollowUp;

@Service
@Transactional
public class FollowUpService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private FollowUpRepository	followUpRepository;

	// Supporting services ----------------------------------------------------
	private UserService			userService;


	// Constructors -----------------------------------------------------------
	public FollowUpService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public FollowUp create() {
		Date publicationMoment;
		FollowUp followUp;
		//User userConnected;

		publicationMoment = new Date(System.currentTimeMillis() - 1000);
		//Comprobamos que sea un usuario
		this.userService.checkPrincipal();
		//userConnected = this.userService.findByPrincipal();
		followUp = new FollowUp();
		followUp.setPublicationMoment(publicationMoment);

		return followUp;
	}

	public FollowUp save(final FollowUp followUp) {
		return followUp;

	}

	public void delete(final FollowUp followUp) {

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

}
