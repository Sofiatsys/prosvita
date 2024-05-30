package com.team.prosvita.repository;

import com.team.prosvita.entities.Article;
import com.team.prosvita.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByArticleStatus(Status status);
    List<Article> findByArticleStatusIn(List<Status> statuses);
}
