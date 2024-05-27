package com.team.prosvita.controller;

import com.team.prosvita.entities.Article;
import com.team.prosvita.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private final ArticleService articleService;
    @Autowired
    public HomeController(ArticleService articleService) {
        this.articleService = articleService;
    }

//    @GetMapping("/home")
//    public String home() {
//        return "home";
//    }


    @GetMapping("/home")
    public String getAllArticles(Model model) {
        model.addAttribute("articles", articleService.getAllArticles());
        return "home";
    }

    @GetMapping("/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = articleService.getArticleById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid article Id:" + id));
        model.addAttribute("article", article);
        model.addAttribute("state", "read");
        return "article";
    }
}
