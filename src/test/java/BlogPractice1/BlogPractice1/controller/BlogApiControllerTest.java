package BlogPractice1.BlogPractice1.controller;

import BlogPractice1.BlogPractice1.domain.Article;
import BlogPractice1.BlogPractice1.dto.AddArticleRequest;
import BlogPractice1.BlogPractice1.dto.ArticleResponse;
import BlogPractice1.BlogPractice1.repository.BlogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class BlogApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper; // 직렬화 역직렬화를 위한 클래스

    @Autowired
    private WebApplicationContext context;

    @Autowired
    BlogRepository blogRepository;

    @BeforeEach
    public void mockMovSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        blogRepository.deleteAll();
    }

    @DisplayName("블로그 글 추가한다")
    @Test
    public void addArticle() throws Exception {
        // given
        final String url ="/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest request = new AddArticleRequest(title,content);

        // 객체 json으로 직렬화 시킴!
        final String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));
        // then

        // result.andExpect(status().isCreated());
        List<Article> articles =  blogRepository.findAll();

        Assertions.assertThat(articles.size()).isEqualTo(1);
        Assertions.assertThat(articles.get(0).getTitle()).isEqualTo("title");

    }

    @DisplayName("findAllArticles: 블로그 글 목록 조회성공")
    @Test
    public void findAllArticles() throws Exception {
        // given -> 일단 url 설정!
        final String url = "/api/articles";
        final String title = "titless";
        final String content = "contentss";

        blogRepository.save(
                Article.builder().title(title).content(content).build());

        // when -> 데이터 조회!
        final ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        // then -> 확인
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(content));
    }

    @DisplayName("블로그 글 조회에 성공한다")
    @Test
    public void findArticle() throws Exception {
        //
        final String url = "/api/articles/{id}";
        final String title = "titless";
        final String content = "contentss";

        Article article  =blogRepository.save(Article.builder().title(title).content(content).build());

        // when
        final ResultActions result = mockMvc.perform(get(url, article.getId()));

        // then
        result.andExpect(status().isOk()).andExpect(jsonPath("$.content").value(content));

    }

    @DisplayName("delete Article")
    @Test
    public void deleteArticleTest() throws Exception {
        final String url = "/api/articles/{id}";
        final String title = "titless";
        final String content = "contentss";

        Article article = blogRepository.save(Article.builder().title(title).content(content).build());

        mockMvc.perform(delete(url, article.getId())).andExpect(status().isOk());

        List<Article> articles = blogRepository.findAll();
        Assertions.assertThat(articles.size()).isEqualTo(0);

    }

}