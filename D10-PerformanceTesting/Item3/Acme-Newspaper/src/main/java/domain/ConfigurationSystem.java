
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Access(AccessType.PROPERTY)
public class ConfigurationSystem extends DomainEntity {

	private Collection<String>	tabooWords;


	@ElementCollection
	@NotEmpty
	@NotNull
	public Collection<String> getTabooWords() {
		return this.tabooWords;
	}

	public void setTabooWords(final Collection<String> tabooWords) {
		this.tabooWords = tabooWords;
	}

}
