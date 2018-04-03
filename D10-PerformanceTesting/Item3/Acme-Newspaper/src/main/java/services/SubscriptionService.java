
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SubscriptionRepository;
import domain.Subscription;

@Service
@Transactional
public class SubscriptionService {

	// Managed repository -----------------------------------------------------

	@Autowired
	SubscriptionRepository	subscriptionRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	CustomerService			customerService;


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

	//SAVE
	public Subscription save(final Subscription subscription) {
		Assert.notNull(subscription);
		//Assert.isTrue(expression)
		Assert.isTrue(!subscription.getNewspaper().isOpen());
		return null;
	}
	// Other business methods -------------------------------------------------

}
