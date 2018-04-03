
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ChirpRepository;
import domain.Chirp;
import domain.User;

@Service
@Transactional
public class ChirpService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ChirpRepository	chirpRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private UserService		userService;

	@Autowired
	private AdminService	adminService;


	// Constructors -----------------------------------------------------------
	public ChirpService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Chirp create() {
		Date postedMoment;
		Chirp chirp;
		User userConnected;

		postedMoment = new Date(System.currentTimeMillis() - 1000);
		userConnected = this.userService.findByPrincipal();
		chirp = new Chirp();
		chirp.setPostedMoment(postedMoment);
		chirp.setUser(userConnected);

		return chirp;
	}

	public Chirp save(final Chirp chirp) {
		Chirp result;
		Assert.notNull(chirp);

		if (chirp.getId() != 0) {
			final Date now = new Date();
			Assert.isTrue(chirp.getPostedMoment().after(now));
		}
		result = this.chirpRepository.save(chirp);
		return result;

	}

	public void delete(final Chirp chirp) {
		Assert.notNull(chirp);
		Assert.isTrue(chirp.getId() != 0);

		this.adminService.checkPrincipal();

	}

	// Other business methods -------------------------------------------------
	public Collection<Chirp> getChirpsOfMyFollowers(final int userId) {
		Collection<Chirp> result;
		result = this.chirpRepository.getChirpsOfMyFollowers(userId);
		return result;
	}
}
