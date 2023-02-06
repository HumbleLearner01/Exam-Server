package com.rest.service.question.impl;

import com.rest.model.question.Category;
import com.rest.model.question.Quiz;
import com.rest.repository.question.QuizRepository;
import com.rest.service.question.QuizService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Data
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepo;

    @Override
    public Quiz addQuiz(Quiz quiz) {
        return quizRepo.save(quiz);
    }

    @Override
    public Quiz updateQuiz(Quiz quiz) {
        return quizRepo.save(quiz);
    }

    @Override
    public Quiz getQuiz(Long quizId) {
        return quizRepo.findById(quizId).get();
    }

    @Override
    public Set<Quiz> getAllQuizzes() {
        return new HashSet<>(quizRepo.findAll());
    }

    @Override
    public void deleteQuiz(Long quizId) {
        quizRepo.deleteById(quizId);
    }

    @Override
    public List<Quiz> getQuizByCategory(Category category) {
        return quizRepo.findByCategory(category);
    }

    //getting active categories : used specifically for user and not admin
    @Override public List<Quiz> getActiveQuiz() {
        return quizRepo.findByActive(true);
    }
    @Override public List<Quiz> getQuizByCategoryAndActive(Category category) {
        return quizRepo.findByCategoryAndActive(category, true);
    }
    //end of getting active categories : used specifically for user and not admin
}