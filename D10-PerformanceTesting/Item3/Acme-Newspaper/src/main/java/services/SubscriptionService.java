
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.SubscriptionRepository;
import domain.Subscription;

@Service
@Transactional
public class SubscriptionService {

	// Managed repository -----------------------------------------------------

	@Autowired
	SubscriptionRepository	subscriptionRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public SubscriptionService() {

	}

	// Simple CRUD methods ----------------------------------------------------

	//CREATE
	public Subscription create() {
		Subscription result;

		result = new Subscription();

		return result;
	}

	// Other business methods -------------------------------------------------

}
