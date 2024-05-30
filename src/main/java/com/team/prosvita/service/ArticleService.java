package com.team.prosvita.service;

import com.team.prosvita.entities.Article;
import com.team.prosvita.entities.Status;
import com.team.prosvita.entities.SubjectArea;
import com.team.prosvita.entities.User;
import com.team.prosvita.repository.ArticleRepository;
import com.team.prosvita.repository.SubjectAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final SubjectAreaRepository subjectAreaRepository;


    public List<Article> getArticlesByStatus(Status status) {
        return articleRepository.findByArticleStatus(status);
    }

    public List<Article> getArticlesByStatuses(List<Status> statuses) {
        return articleRepository.findByArticleStatusIn(statuses);
    }

    @Autowired
    public ArticleService(ArticleRepository articleRepository, SubjectAreaRepository subjectAreaRepository) {
        this.articleRepository = articleRepository;
        this.subjectAreaRepository = subjectAreaRepository;
    }

    public List<SubjectArea> getAllSubjectAreas() {return subjectAreaRepository.findAll();
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    public Article save(Article article) {
        return articleRepository.save(article);
    }

    public Article update(Article article) { return articleRepository.save(article); }

    public void delete(Long id) { articleRepository.deleteById(id); }


}
