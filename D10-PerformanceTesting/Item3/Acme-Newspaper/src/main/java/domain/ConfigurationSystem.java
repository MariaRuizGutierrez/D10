
package domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Access(AccessType.PROPERTY)
public class ConfigurationSystem extends DomainEntity {

	private Collection<String>	tabooWordsDefault;
	private Collection<String> tabooWordsNew;
	
	@ElementCollection
	@NotEmpty
	@NotNull
	public Collection<String> getTabooWordsDefault() {
		return this.tabooWordsDefault;
	}

	public void setTabooWordsDefault(final Collection<String> tabooWordsDefault) {
		this.tabooWordsDefault = tabooWordsDefault;
	}

	@ElementCollection
	@NotNull
	public Collection<String> getTabooWordsNew() {
		return tabooWordsNew;
	}

	
	public void setTabooWordsNew(Collection<String> tabooWordsNew) {
		this.tabooWordsNew = tabooWordsNew;
	}

	@Transient
	public Collection<String> getTabooWords() {
		Collection<String> tabooWords;
		
		tabooWords = new ArrayList<>();
		
		tabooWords.addAll(this.getTabooWordsDefault());
		tabooWords.addAll(this.getTabooWordsNew());
		
		return tabooWords;
	}
	

}
