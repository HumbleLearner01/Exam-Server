package com.rest.service.question;

import com.rest.model.question.Question;
import com.rest.model.question.Quiz;

import java.util.Set;

public interface QuestionService {
    Question addQuestion(Question qstn);
    Question updateQuestion(Question qstn);
    Question getQuestion(Long qstn);
    Set<Question> getAllQuestionsOfQuiz(Quiz quiz);
    void deleteQuestion(Long qstn);
    Question getQuestionById(Long qstnId);
}
