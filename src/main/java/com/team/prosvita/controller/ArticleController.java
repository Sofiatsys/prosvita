package com.team.prosvita.controller;

import com.team.prosvita.entities.Article;
import com.team.prosvita.entities.User;
import com.team.prosvita.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String getAllArticles(Model model) {
        model.addAttribute("articles", articleService.getAllArticles());
        return "articles";
    }

    @GetMapping("/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = articleService.getArticleById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid article Id:" + id));
        model.addAttribute("article", article);
        model.addAttribute("state", "read");
        return "article";
    }

    @GetMapping("/create")
    public String createArticleForm(Model model) {
        model.addAttribute("article", new Article());
        model.addAttribute("subjectAreas", articleService.getAllSubjectAreas());
        model.addAttribute("state", "create");
        return "createArticle";
    }

    @PostMapping
    public String createArticle(@ModelAttribute Article article, @AuthenticationPrincipal User activeUser) {
        // TODO redirect if error everywhere
        article.setOwner(activeUser);
        Article createdArticle = articleService.save(article);
        return "redirect:/articles/" + createdArticle.getId();
    }
}
