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
@RequestMapping("/validate_exam")
@RequiredArgsConstructor
public class ExamController {

    private final OpenAIService openAIService;

    @GetMapping
    public ResponseEntity<String> testOpenAI(@RequestParam(required = false) String prompt) {
        if (prompt == null || prompt.trim().isEmpty()) {
            return ResponseEntity.ok("empty");
            // return ResponseEntity.badRequest().body("Prompt parameter is required");
        }

        try {
            String response = openAIService.makeOpenAIRequest("Grade the following code with an integer number according to the problem, maximum score, html part, css part, and javascript part. " + prompt +
                                                                        " Now, you should give partial grade out of total score to the given code. You can be generous." +
                                                                        " Write only an integer number without any comments.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

}
