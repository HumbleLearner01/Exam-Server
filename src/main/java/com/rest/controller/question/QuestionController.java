package com.rest.controller.question;

import com.rest.model.question.Question;
import com.rest.model.question.Quiz;
import com.rest.service.question.QuestionService;
import com.rest.service.question.QuizService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/question")
@Data
public class QuestionController {
    private final QuestionService questionService;
    private final QuizService quizService;

    //add a question
    @PostMapping("")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        return ResponseEntity.ok(questionService.addQuestion(question));
    }

    //update a question
    @PutMapping("")
    public ResponseEntity<Question> updateQuestion(@RequestBody Question question) {
        return ResponseEntity.ok(questionService.updateQuestion(question));
    }

    //get all question of a quiz for user
    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<?> getAllQuestionsOfQuiz(@PathVariable Long quizId) {
        Quiz quiz = quizService.getQuiz(quizId);
        Set<Question> questions = quiz.getQuestions();
        List<Question> list = new ArrayList<>(questions);
        if (list.size() > Integer.parseInt(quiz.getNumberOfQuestions()))
            list = list.subList(0, Integer.parseInt(quiz.getNumberOfQuestions() + 1));

//        this is the important point that we said inside question entity for the answer property to not get sent to the client side
        list.forEach((q)->q.setAnswer(""));

        Collections.shuffle(list);
        return ResponseEntity.ok(list);
    }

    //get all question of a quiz for admin
    @GetMapping("/quiz/admin/all/{quizId}")
    public ResponseEntity<?> getAllQuestionsOfQuizForAdmin(@PathVariable Long quizId) {
        Quiz quiz = new Quiz();
        quiz.setQuizId(quizId);
        return ResponseEntity.ok(questionService.getAllQuestionsOfQuiz(quiz));
    }

    //get single question
    @GetMapping("/{qstId}")
    public ResponseEntity<Question> getSingleQuestion(@PathVariable("qstId") Long qstId) {
        return ResponseEntity.ok(questionService.getQuestion(qstId));
    }

    //delete a question
    @DeleteMapping("/{qstId}")
    public void deleteQuestion(@PathVariable("qstId") Long qstId) {
        questionService.deleteQuestion(qstId);
    }

    //evaluate the user's given answer
    @PostMapping("/eval-answer")
    public ResponseEntity<?> evaluateAnswer(@RequestBody List<Question> questions) {
        int correctAnswer = 0, marksGot = 0, attempted = 0;
        for (Question submittedQuestion : questions) {
            Question databaseQuestion = questionService.getQuestionById(submittedQuestion.getQstId());
            if (databaseQuestion.getAnswer().equals(submittedQuestion.getGivenAnswer())) {
                correctAnswer++;
                double singleMark = Double.parseDouble(questions.get(0).getQuiz().getMaxMarks()) / questions.size();
                marksGot += singleMark;
            }
            if (submittedQuestion.getGivenAnswer() != null)
                attempted++;
        }
        Map<String, Object> map = Map.of("correctAnswer", correctAnswer, "marksGot", marksGot, "attempted", attempted);
        return ResponseEntity.ok(map);
    }
}
