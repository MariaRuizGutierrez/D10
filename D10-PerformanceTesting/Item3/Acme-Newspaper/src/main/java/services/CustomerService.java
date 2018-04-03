
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Customer;

@Service
@Transactional
public class CustomerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	CustomerRepository	customerRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	// Simple CRUD methods ----------------------------------------------------

	// Other business methods -------------------------------------------------

	public Customer findByPrincipal() {

		Customer result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.customerRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}
}
