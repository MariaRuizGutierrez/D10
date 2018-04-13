
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {

	//Relationships-------------------------------------------------------------

	private Collection<Subscription>	subcriptions;


	@OneToMany(mappedBy = "customer")
	@Valid
	public Collection<Subscription> getSubcriptions() {
		return this.subcriptions;
	}

	public void setSubcriptions(final Collection<Subscription> subcriptions) {
		this.subcriptions = subcriptions;
	}

}
