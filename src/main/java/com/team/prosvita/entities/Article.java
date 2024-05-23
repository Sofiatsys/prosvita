package com.team.prosvita.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @ManyToOne
    @JoinColumn(name = "subject_area_id", nullable = false)
    private SubjectArea subjectArea;
    @Column(nullable = false, columnDefinition = "json")
    private String content;
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    /*@Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ArticleStatus status;*/
    @Column(name = "created_at")
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
    @Column(name = "updated_at")
    private Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
    // TODO: tags field?
}