package com.rest.model.question;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Question {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long qstId;
    private String content;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

//    @JsonIgnore --> this is a bug. won't throw any error but, when admin is logged in, when he/she submits an answer, the answer won't get saved in the database!
//    for this bug to get taken care of, we'll use "@JsonIgnore" at the getter of this property and "@JsonProperty" at the setter of this property.
//    but there will be another bug, the correct answer won't get shown on the screen even for the admin itself since we have ignored the getter value of this property.
//    we'll use a foreach loop and make the answer as blank using setter inside the "getAllQuestionsOfQuiz() this is for user to get shown by the number of question".
    private String answer;

    private String image;
    @Transient
    private String givenAnswer;

    /*mapping*/
    @ManyToOne(fetch = FetchType.EAGER)
    private Quiz quiz;
    /*end of mapping*/
}