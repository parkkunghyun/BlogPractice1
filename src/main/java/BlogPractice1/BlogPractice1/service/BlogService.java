package BlogPractice1.BlogPractice1.service;

import BlogPractice1.BlogPractice1.domain.Article;
import BlogPractice1.BlogPractice1.dto.AddArticleRequest;
import BlogPractice1.BlogPractice1.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    @Autowired
    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    public List<Article> findAll() {
        return new ArrayList<>(blogRepository.findAll());
    }

    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found:" + id));
    }

    public void delete(long id) {
        blogRepository.deleteById(id);
    }
}
