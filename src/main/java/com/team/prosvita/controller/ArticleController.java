package com.team.prosvita.controller;

import com.team.prosvita.entities.Article;
import com.team.prosvita.entities.User;
import com.team.prosvita.service.ArticleService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;


@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;
    @Setter
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

//    @GetMapping
//    public String getAllArticles(Model model) {
//        model.addAttribute("articles", articleService.getAllArticles());
//        return "articles";
//    }

//    @GetMapping("/{id}")
//    public String getArticle(@PathVariable Long id, Model model) {
//        Article article = articleService.getArticleById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid article Id:" + id));
//        model.addAttribute("article", article);
//        model.addAttribute("state", "read");
//        return "article";
//    }

    @GetMapping("/create")
    public String createArticleForm(Model model) {
        model.addAttribute("article", new Article());
        model.addAttribute("subjectAreas", articleService.getAllSubjectAreas());
        model.addAttribute("state", "create");
        return "createArticle";
    }

    @PostMapping
    public String createArticle(@ModelAttribute Article article, @RequestParam("imageFile") MultipartFile imageFile, @AuthenticationPrincipal User activeUser) throws IOException {
        // Handle image upload and save the URL to the article
        if (!imageFile.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + imageFile.getOriginalFilename();
            //String imageUrl = "/path/to/upload/directory/" + imageFile.getOriginalFilename();
            imageFile.transferTo(new File(uploadPath + "/" + resultFilename));
            article.setImageUrl(resultFilename);
        }

        article.setOwner(activeUser);
        articleService.save(article);
        return "redirect:/home";
    }

    @GetMapping("/edit/{id}")
    public String editArticleForm(@PathVariable Long id, Model model) {
        Article article = articleService.getArticleById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid article Id:" + id));
        model.addAttribute("article", article);
        model.addAttribute("subjectAreas", articleService.getAllSubjectAreas());
        model.addAttribute("state", "edit");
        return "edit-article";
    }

    @PostMapping("/update")
    public String updateArticle(@ModelAttribute Article article, @RequestParam("imageFile") MultipartFile imageFile, @AuthenticationPrincipal User activeUser) throws IOException {
        if (!imageFile.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + imageFile.getOriginalFilename();
            imageFile.transferTo(new File(uploadPath + "/" + resultFilename));
            article.setImageUrl(resultFilename);
        } else {
            Article existingArticle = articleService.getArticleById(article.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid article Id:" + article.getId()));
            article.setImageUrl(existingArticle.getImageUrl());
        }

        article.setOwner(activeUser);
        articleService.update(article);
        return "redirect:/home";
    }
    @PostMapping("/delete/{id}")
    public String deleteArticle(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Article> optionalArticle = articleService.getArticleById(id);

        if (optionalArticle.isPresent()) {
            articleService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Article deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Article not found.");
        }
        return "redirect:/home";
    }
}
