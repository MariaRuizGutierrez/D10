package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.TabooWord;

import repositories.TabooWordRepository;

@Service
@Transactional
public class TabooWordService {
	
	// Managed repository -----------------------------------------------------
	@Autowired
	private TabooWordRepository	tabooWordRepository;
		
	// Supporting services ----------------------------------------------------
		
	@Autowired
	private AdminService	adminService;
	
	// Constructors -----------------------------------------------------------
		
	public TabooWordService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public TabooWord create(){
		
		TabooWord result;
		
		result = new TabooWord();
		result.setDefault_word(false);
		
		return result;
		
	}
	
	public TabooWord findOne(int tabooWordId){
		
		TabooWord result;
		
		Assert.notNull(tabooWordId);
		Assert.isTrue(tabooWordId != 0);
		
		result = this.tabooWordRepository.findOne(tabooWordId);
		
		return result;
		
	}
	
	public Collection<TabooWord> findAll(){
		
		Collection<TabooWord> result;
		
		result = this.tabooWordRepository.findAll();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public TabooWord save(TabooWord tabooWord){
		
		Assert.notNull(tabooWord);
		
		this.adminService.checkPrincipal();
		
		TabooWord result;
		
		result = this.tabooWordRepository.save(tabooWord);
		
		return result;
	}
	
	public void delete(TabooWord tabooWord){
		
		Assert.notNull(tabooWord);
		Assert.isTrue(tabooWord.getId() != 0);
		
		this.tabooWordRepository.delete(tabooWord);
		
	}

	//Other 
	
	public Collection<String> findTabooWordByName(){
		
		Collection<String> result;
		
		result = this.tabooWordRepository.findTabooWordByName();
		
		return result;
	}
}
