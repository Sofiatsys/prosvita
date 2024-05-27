package com.team.prosvita.controller;

import com.team.prosvita.entities.Article;
import com.team.prosvita.entities.SubjectArea;
import com.team.prosvita.entities.User;
import com.team.prosvita.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class HomeControllerTests {

    @Mock
    private ArticleService articleService;

    @Mock
    private Model model;

    @InjectMocks
    private HomeController homeController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllArticles() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article());
        articles.add(new Article());
        when(articleService.getAllArticles()).thenReturn(articles);

        String viewName = homeController.getAllArticles(model);

        assertEquals("home", viewName);
        verify(model).addAttribute("articles", articles);
    }

    @Test
    public void testGetArticle() {
        long articleId = 1L;
        Article article = new Article(articleId, "test", new SubjectArea(),"test content",new User(),"test url",new Timestamp(100), new Timestamp(100) );
        when(articleService.getArticleById(articleId)).thenReturn(Optional.of(article));

        String viewName = homeController.getArticle(articleId, model);

        assertEquals("article", viewName);
        verify(model).addAttribute("article", article);
        verify(model).addAttribute("state", "read");
    }

    @Test
    public void testGetArticle_InvalidId() {
        long invalidId = 100L;
        when(articleService.getArticleById(invalidId)).thenReturn(Optional.empty());

        IllegalArgumentException exception =
            org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
            () -> homeController.getArticle(invalidId, model));
        assertEquals("Invalid article Id:" + invalidId, exception.getMessage());

        verify(model, never()).addAttribute(any(), any());
    }
}
