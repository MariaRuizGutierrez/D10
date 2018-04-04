
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Article;
import domain.User;

@Service
@Transactional
public class UserService {

	// Managed repository -----------------------------------------------------

	@Autowired
	UserRepository	userRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public UserService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public User create() {

		User result;
		UserAccount userAccount;
		Authority authority;
		Collection<Article> articles;
		Collection<User> followers;
		Collection<User> followed;

		result = new User();
		userAccount = new UserAccount();
		authority = new Authority();
		articles = new ArrayList<>();
		followers = new ArrayList<>();
		followed = new ArrayList<>();

		authority.setAuthority(Authority.USER);
		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);
		result.setArticles(articles);
		result.setFollowers(followers);
		result.setFollowed(followed);
		return result;

	}

	public User save(final User user) {
		Assert.notNull(user);
		User result;
		Md5PasswordEncoder encoder;
		String passwordHash;

		if (user.getId() == 0) {
			encoder = new Md5PasswordEncoder();
			passwordHash = encoder.encodePassword(user.getUserAccount().getPassword(), null);
			user.getUserAccount().setPassword(passwordHash);
		}
		result = this.userRepository.save(user);
		Assert.notNull(result);
		return result;
	}

	public void delete(final User user) {
		Assert.notNull(user);
		Assert.isTrue(user.getId() != 0);
		this.userRepository.delete(user);

	}

	public Collection<User> findAll() {
		Collection<User> result;

		result = this.userRepository.findAll();
		return result;
	}

	public User findOne(final int userId) {
		Assert.isTrue(userId != 0);
		User result;
		result = this.userRepository.findOne(userId);

		return result;
	}

	// Other business methods -------------------------------------------------

	public User findByPrincipal() {

		User result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.userRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public void checkPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();

		Assert.notNull(userAccount);

		final Collection<Authority> authorities = userAccount.getAuthorities();
		Assert.notNull(authorities);

		final Authority auth = new Authority();
		auth.setAuthority("RANGER");

		Assert.isTrue(authorities.contains(auth));
	}
}
