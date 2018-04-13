
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Article;
import domain.Newspaper;

@Repository
public interface NewspaperRepository extends JpaRepository<Newspaper, Integer> {

	//Me devuelve el newspaper que pertenece al articulo dado
	@Query("select n from Newspaper n join n.articles a where a.id = ?1")
	Newspaper findByArticleId(int articleId);

	//Me devuelve todos los articulos de un newspaper dado que estan en modo borrador
	@Query("select a from Newspaper n join n.articles a where n.id = ?1 and a.draftMode=true")
	Collection<Article> isAllFinalMode(int newsPaperId);

	//Me devuelve todos los newspapers de un usuario dado
	@Query("select n from Newspaper n where n.publisher.id=?1")
	Collection<Newspaper> findNewspapersCreatedByUser(int userId);

	//Me devuelven las newspapers publicas que estan publicadas
	@Query("select n from Newspaper n where n.open=true and n.publicationDate!=null")
	Collection<Newspaper> findNewspapersPublishedAndOpen();

	//Me devuelven las newspapers que estan publicadas
	@Query("select n from Newspaper n where n.publicationDate!=null")
	Collection<Newspaper> findNewspapersPublished();

	//Me devuelven los newspapers que aun no han sido publicados
	@Query("select n from Newspaper n where n.publicationDate=null")
	Collection<Newspaper> findNewspaperNotPublished();

	//Buscador newspaper
	@Query("select n from Newspaper n where (n.title like %?1% or n.description like %?1%) and n.publicationDate!=null and n.open=true")
	Collection<Newspaper> findNewspapersByKeyword(String keyWord);

	@Query("select n from Newspaper n where (n.title like %?1% or n.description like %?1%) and (n.publicationDate!=null)")
	Collection<Newspaper> findNewspapersForUser(String keyWord);

	//Buscador newspaper USERS
	@Query("select n from Newspaper n where (n.title like %?1% or n.description like %?1%) and n.publicationDate!=null")
	Collection<Newspaper> findNewspapersByKeywordAuthenticate(String keyWord);

	@Query("select distinct s.newspaper from Subscription s where s.customer.id=?1")
	Collection<Newspaper> findNewspapersByCustomerId(int customerId);

	@Query("select n from Newspaper n where n.publisher.id=?1")
	Collection<Newspaper> findByUserId(int userId);

	@Query("select n from Newspaper n where n.open=false and n.publicationDate!=null")
	Collection<Newspaper> findPrivateAndPublicatedNewspapersToSubscribe();

	@Query("select s.newspaper from Subscription s where s.customer.id=?1")
	Collection<Newspaper> findNewspapersSubscribedByCustomerId(int customerId);

	//Me devuelve los newspaper con alguna palabra en el título, cuerpo o resumen (para las palabras tabú)
	@Query("select n from Newspaper n where n.title like %?1% or n.description like %?1%")
	Collection<Newspaper> findNewspaperWithTabooWord(String tabooWord);

	//
	@Query("select n from Newspaper n where (n.title like %?1% or n.description like %?1%)")
	Collection<Newspaper> findAllNewspapersByAdmin(String keyWord);

}
