package com.rest.service.question;

import com.rest.model.question.Category;
import com.rest.model.question.Quiz;

import java.util.List;
import java.util.Set;

public interface QuizService {
    Quiz addQuiz(Quiz quiz);
    Quiz updateQuiz(Quiz quiz);
    Quiz getQuiz(Long quizId);
    Set<Quiz> getAllQuizzes();
    void deleteQuiz(Long quizId);
    List<Quiz> getQuizByCategory(Category category);
    List<Quiz> getActiveQuiz();
    List<Quiz> getQuizByCategoryAndActive(Category category);
}
