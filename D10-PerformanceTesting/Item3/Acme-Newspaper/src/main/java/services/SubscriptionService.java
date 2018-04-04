
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SubscriptionRepository;
import domain.Customer;
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
		Customer customerPrincipal;

		customerPrincipal = this.customerService.findByPrincipal();
		result = new Subscription();
		result.setCustomer(customerPrincipal);

		return result;
	}

	//SAVE
	public Subscription save(final Subscription subscription) {
		Customer customerPrincipal;
		Subscription result;

		Assert.notNull(subscription);
		customerPrincipal = this.customerService.findByPrincipal();
		Assert.isTrue(subscription.getCustomer().equals(customerPrincipal), "El cliente de la subscripcion debe ser el mismo que el logueado");
		Assert.isTrue(!subscription.getNewspaper().isOpen(), "solo se pueden subscribir a los periodicos privados");
		Assert.notNull(subscription.getNewspaper().getPublicationDate(), "solo se pueden subscribir a los periodicos publicados");

		result = this.subscriptionRepository.save(subscription);
		return result;
	}

	public void flush() {
		this.subscriptionRepository.flush();
	}

	// Other business methods -------------------------------------------------

}
