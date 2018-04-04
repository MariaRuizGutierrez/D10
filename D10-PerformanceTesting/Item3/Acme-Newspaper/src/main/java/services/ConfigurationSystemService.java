
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ConfigurationSystemRepository;
import domain.ConfigurationSystem;

@Service
@Transactional
public class ConfigurationSystemService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ConfigurationSystemRepository	configurationSystemRepository;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private AdminService	adminService;

	// Constructors -----------------------------------------------------------
	
	public ConfigurationSystemService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	
	public Collection<ConfigurationSystem> findAll() {
		Collection<ConfigurationSystem> result;

		Assert.notNull(this.configurationSystemRepository);
		result = this.configurationSystemRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public ConfigurationSystem findOne(int configurationSystemId) {
		
		Assert.isTrue(configurationSystemId != 0);
		
		ConfigurationSystem res;
		
		res = this.configurationSystemRepository.findOne(configurationSystemId);
		

		return res;

	}

	public ConfigurationSystem save(ConfigurationSystem configurationSystem) {
		Assert.notNull(configurationSystem);
		
		this.adminService.checkPrincipal();

		ConfigurationSystem result;

		result = this.configurationSystemRepository.save(configurationSystem);

		return result;
	}
	
	public ConfigurationSystem addTabooWord(ConfigurationSystem configurationSystem, String tabooWord) {
		Assert.notNull(configurationSystem);
		Assert.notNull(tabooWord);
		Assert.isTrue(!tabooWord.equals(""));
		
		this.adminService.checkPrincipal();

		ConfigurationSystem result;
		
		configurationSystem.getTabooWords().add(tabooWord);
		
		result = this.configurationSystemRepository.save(configurationSystem);

		return result;
	}



	// Other business methods -------------------------------------------------
	public void flush() {
		this.configurationSystemRepository.flush();
	}

}
