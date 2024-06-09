package CourseHelper.itu.Controller;

import CourseHelper.itu.openAI.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequiredArgsConstructor
public class ExamController {

    private final OpenAIService openAIService;

    @GetMapping("/grade")
    public ResponseEntity<String> getExamGrading(@RequestParam(required = false) String prompt) {
        String response = openAIService.getExamGrading(prompt);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/review")
    public ResponseEntity<String> getExamReview(@RequestParam(required = false) String prompt) {
        String response = openAIService.getExamReview(prompt);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/question")
    public ResponseEntity<String> getExamQuestion(@RequestParam(required = false) String prompt) {
        String response = openAIService.getExamQuestion(prompt);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/help")
    public ResponseEntity<String> getExamHelp(@RequestParam(required = false) String prompt) {
        String response = openAIService.getExamHelp(prompt);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
