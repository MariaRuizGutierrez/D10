
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

	@Query("select a from Article a where (a.title like '%1%' or a.summary like '%1%' or a.body like '%1%')")
	Collection<Article> findArticlesByKeyword(String keyWord);

	@Query("select a from Article a where a.newspaper.id=?1")
	Collection<Article> findArticlesByNewspaperId(int newspaperId);

	@Query("select a from Article a where a.writer.id=?1 and a.publishedMoment!=null")
	Collection<Article> findArticlesPublishedByUserId(int newspaperId);
}
