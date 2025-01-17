package com.team.prosvita.controller;

import com.team.prosvita.entities.Article;
import com.team.prosvita.entities.Status;
import com.team.prosvita.entities.User;
import com.team.prosvita.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

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


//    @GetMapping("/home")
//    public String getAllArticles(Model model) {
//        model.addAttribute("articles", articleService.getAllArticles());
//        return "home";
//    }

    @GetMapping("/home")
    public String getAllArticles(Model model, Authentication authentication) {

        String role = authentication.getAuthorities().iterator().next().getAuthority();

        List<Article> articles;
        articles = articleService.getArticlesByStatus(Status.VERIFIED);

        model.addAttribute("articles", articles);

        if (role.equals("ADMIN")) {
            model.addAttribute("isAdmin", true);
        } else {
            model.addAttribute("isAdmin", false);
        }

        return "home";
    }

    /*@GetMapping("/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = articleService.getArticleById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid article Id:" + id));
        model.addAttribute("article", article);
        model.addAttribute("state", "read");
        return "article";
    }*/

    @GetMapping("/{id}")
    public String getArticle(@PathVariable Long id, Model model, @AuthenticationPrincipal User activeUser) {
        Article article = articleService.getArticleById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid article Id:" + id));
        model.addAttribute("article", article);
        model.addAttribute("state", "read");
        model.addAttribute("currentUserId", activeUser.getId()); // record current id of user to know if it is the users article
        return "article";
    }
}
