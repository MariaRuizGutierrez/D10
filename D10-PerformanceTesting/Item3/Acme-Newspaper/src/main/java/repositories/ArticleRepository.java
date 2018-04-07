
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

	@Query("select a from Article a where (a.title like %?1% or a.summary like %?1% or a.body like %?1%) and a.publishedMoment!=null and a.newspaper.open=true and a.newspaper.publicationDate!=null")
	Collection<Article> findArticlesByKeyword(String keyWord);

	@Query("select a from Article a where a.newspaper.id=?1 and a.publishedMoment!=null")
	Collection<Article> findArticlesByNewspaperId(int newspaperId);

	@Query("select a from Article a where a.writer.id=?1 and a.publishedMoment!=null")
	Collection<Article> findArticlesPublishedByUserId(int newspaperId);

	@Query("select a.summary from Article a where a.id=?1")
	String findSummaryByArticleId(int articleId);
	
	//Query para el caso 7.1. Un admin podrá borrar aquellos articulos que aún no han sido publicados
	@Query("select a from Article a where a.newspaper.publicationDate=null")
	Collection<Article> findArticleNewspaperNoPublished();
}
