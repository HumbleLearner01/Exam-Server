package com.rest.model.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long quizId;
    private String title;
    @Lob
    private String description;
    private String maxMarks;
    private String numberOfQuestions;
    private boolean active = false;

    /*mapping*/
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) //--> this throws {org.springframework.dao.InvalidDataAccessApiUsageException: detached entity passed to persist: Category;}
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Question> questions = new HashSet<>();
    /*end of mapping*/
}
