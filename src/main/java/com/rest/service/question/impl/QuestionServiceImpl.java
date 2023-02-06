package com.rest.service.question.impl;

import com.rest.model.question.Question;
import com.rest.model.question.Quiz;
import com.rest.repository.question.QuestionRepository;
import com.rest.service.question.QuestionService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Data
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepo;

    @Override
    public Question addQuestion(Question qstn) {
        return questionRepo.save(qstn);
    }

    @Override
    public Question updateQuestion(Question qstn) {
        return questionRepo.save(qstn);
    }

    @Override
    public Question getQuestion(Long qstn) {
        return questionRepo.findById(qstn).get();
    }

    @Override
    public Set<Question> getAllQuestionsOfQuiz(Quiz quiz) {
        return questionRepo.findByQuiz(quiz);
    }

    @Override
    public void deleteQuestion(Long qstn) {
        Question question = new Question();
        question.setQstId(qstn);
        questionRepo.delete(question);
    }

    @Override
    public Question getQuestionById(Long qstnId) {
        return questionRepo.getOne(qstnId);
    }
}
