package com.team.prosvita.controller;

import com.team.prosvita.entities.Article;
import com.team.prosvita.entities.User;
import com.team.prosvita.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ArticleControllerTests {

    @Mock
    private ArticleService articleService;

    @Mock
    private Model model;

    @Mock
    private MultipartFile imageFile;

    @Mock
    private User activeUser;

    @InjectMocks
    private ArticleController articleController;

    @Value("${upload.path}")
    private String uploadPath;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        articleController = new ArticleController(articleService);
        //  uploadPath must be manually set
        articleController.setUploadPath(uploadPath);

    }

    @Test
    public void testCreateArticleForm() {
        when(articleService.getAllSubjectAreas()).thenReturn(Collections.emptyList());

        String viewName = articleController.createArticleForm(model);

        verify(model, times(1)).addAttribute(eq("article"), any(Article.class));
        verify(model, times(1)).addAttribute(eq("subjectAreas"), anyList());
        verify(model, times(1)).addAttribute("state", "create");
        assertEquals("createArticle", viewName);
    }

    @Test
    public void testCreateArticle() throws IOException {
        Article article = new Article();
        when(imageFile.isEmpty()).thenReturn(false);
        when(imageFile.getOriginalFilename()).thenReturn("testImage.jpg");

        String uuid = UUID.randomUUID().toString();
        doNothing().when(imageFile).transferTo(any(File.class));

        String viewName = articleController.createArticle(article, imageFile, activeUser);

        verify(imageFile, times(1)).transferTo(any(File.class));
        verify(articleService, times(1)).save(article);
        assertEquals("redirect:/home", viewName);
    }
}
