package BlogPractice1.BlogPractice1.repository;

import BlogPractice1.BlogPractice1.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Article, Long> {
}
