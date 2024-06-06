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
@RequestMapping("/validate_question")
@RequiredArgsConstructor
public class QuestionController {

    private final OpenAIService openAIService;

    @GetMapping
    public ResponseEntity<String> testOpenAI(@RequestParam(required = false) String prompt) {
        if (prompt == null || prompt.trim().isEmpty()) {
            return ResponseEntity.ok("empty");
            // return ResponseEntity.badRequest().body("Prompt parameter is required");
        }

        try {
            String response = openAIService.makeOpenAIRequest("Generate a problem using the features: " + prompt +
                                                                        " You should write a creative question for the user to answer using only given languages.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

}