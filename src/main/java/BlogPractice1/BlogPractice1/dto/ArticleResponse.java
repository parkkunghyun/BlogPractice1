package BlogPractice1.BlogPractice1.dto;

import BlogPractice1.BlogPractice1.domain.Article;
import lombok.Getter;

@Getter
public class ArticleResponse {
    // api/articles GET 요청시 글 목록을 조회!
    private final String title;
    private final String content;

    public ArticleResponse(Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
