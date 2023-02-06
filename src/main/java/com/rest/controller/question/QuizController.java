package com.rest.controller.question;

import com.rest.model.question.Category;
import com.rest.model.question.Quiz;
import com.rest.service.question.QuizService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/quiz")
@CrossOrigin("*")
@Data
public class QuizController {
    private final QuizService quizService;

    //add quiz
    @PostMapping("")
    public ResponseEntity<Quiz> addQuiz(@RequestBody Quiz quiz) {
        return ResponseEntity.ok(quizService.addQuiz(quiz));
    }

    //update quiz
    @PutMapping("")
    public ResponseEntity<Quiz> updateQuiz(@RequestBody Quiz quiz) {
        return ResponseEntity.ok(quizService.updateQuiz(quiz));
    }

    //get single quiz
    @GetMapping("/{quizId}")
    public Quiz getQuiz(@PathVariable("quizId") Long quizId) {
        return quizService.getQuiz(quizId);
    }

    //get all quizzes
    @GetMapping("")
    public ResponseEntity<Set<Quiz>> getAllQuizzes() {
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }

    //get quiz by category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Quiz>> getQuizzesByCategory(@PathVariable("categoryId") Long categoryId) {
        Category category = new Category();
        category.setCategoryId(categoryId);
        return ResponseEntity.ok(quizService.getQuizByCategory(category));
    }

    //get active quizzes
    @GetMapping("/active")
    public ResponseEntity<List<Quiz>> getActiveQuizzes() {
        return ResponseEntity.ok(quizService.getActiveQuiz());
    }

    //get active quizzes by category
    @GetMapping("/category/active/{categoryId}")
    public ResponseEntity<List<Quiz>> getActiveQuizzesByCategory(@PathVariable("categoryId") Long categoryId) {
        Category category = new Category();
        category.setCategoryId(categoryId);
        return ResponseEntity.ok(quizService.getQuizByCategoryAndActive(category));
    }

    //delete quiz
    @DeleteMapping("/{quizId}")
    public void deleteQuiz(@PathVariable("quizId") Long quizId) {
        quizService.deleteQuiz(quizId);
    }

}
