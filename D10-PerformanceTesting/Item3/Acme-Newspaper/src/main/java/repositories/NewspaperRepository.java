
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Article;
import domain.Newspaper;

@Repository
public interface NewspaperRepository extends JpaRepository<Newspaper, Integer> {

	@Query("select n from Newspaper n join n.articles a where a.id = ?1")
	Newspaper findByArticleId(int articleId);

	//Me devuelve todos los articulos de un newspaper dado que estan en modo borrador
	@Query("select a from Newspaper n join n.articles a where n.id = ?1 and a.draftMode=true")
	Collection<Article> isAllFinalMode(int newsPaperId);

}
