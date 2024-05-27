package com.team.prosvita.service;

import com.team.prosvita.entities.Article;
import com.team.prosvita.entities.SubjectArea;
import com.team.prosvita.repository.ArticleRepository;
import com.team.prosvita.repository.SubjectAreaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTests {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private SubjectAreaRepository subjectAreaRepository;

    @InjectMocks
    private ArticleService articleService;

    @Test
    public void testGetAllSubjectAreas() {
        List<SubjectArea> subjectAreas = Arrays.asList(new SubjectArea(), new SubjectArea());
        when(subjectAreaRepository.findAll()).thenReturn(subjectAreas);

        List<SubjectArea> result = articleService.getAllSubjectAreas();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetAllArticles() {
        List<Article> articles = Arrays.asList(new Article(), new Article(), new Article());
        when(articleRepository.findAll()).thenReturn(articles);

        List<Article> result = articleService.getAllArticles();

        assertEquals(3, result.size());
    }

    @Test
    public void testGetArticleById() {
        Long articleId = 1L;
        Article article = new Article();
        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));

        Optional<Article> result = articleService.getArticleById(articleId);

        assertEquals(article, result.orElse(null));
    }

    @Test
    public void testSaveArticle() {
        Article article = new Article();
        when(articleRepository.save(article)).thenReturn(article);

        Article result = articleService.save(article);

        assertEquals(article, result);
        verify(articleRepository, times(1)).save(article);
    }
}
