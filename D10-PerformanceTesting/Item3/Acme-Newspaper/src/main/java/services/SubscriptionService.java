
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SubscriptionRepository;
import domain.Customer;
import domain.Newspaper;
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

	@Autowired
	NewspaperService		newspaperService;

	//Importar la que pertenece a Spring
	@Autowired
	private Validator		validator;


	// Constructors -----------------------------------------------------------

	public SubscriptionService() {

	}

	// Simple CRUD methods ----------------------------------------------------

	//CREATE
	public Subscription create(final int newspaperId) {
		Subscription result;
		Customer customerPrincipal;
		Newspaper newspaper;

		newspaper = this.newspaperService.findOne(newspaperId);
		Assert.isTrue(!newspaper.isOpen(), "the newspaper must be private");
		customerPrincipal = this.customerService.findByPrincipal();
		result = new Subscription();
		result.setCustomer(customerPrincipal);
		result.setNewspaper(newspaper);

		return result;
	}

	//SAVE
	public Subscription save(final Subscription subscription) {
		Customer customerPrincipal;
		Subscription result;

		Assert.notNull(subscription);
		customerPrincipal = this.customerService.findByPrincipal();
		Assert.isTrue(!customerPrincipal.getSubcriptions().contains(subscription), "el cliente ya esta subscrito a este periodico");
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

	public Subscription reconstruct(final Subscription subscription, final BindingResult binding) {
		Subscription result;
		final Customer customerPrincipal;
		if (subscription.getId() == 0) {

			Assert.isTrue(!subscription.getNewspaper().isOpen(), "the newspaper must be private");
			customerPrincipal = this.customerService.findByPrincipal();
			subscription.setCustomer(customerPrincipal);

			result = subscription;
		} else {
			Assert.notNull(null, "a subscription can not be modified");
			result = subscription;
		}
		this.validator.validate(result, binding);
		return result;
	}

}
