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
@RequestMapping("/validate_css")
@RequiredArgsConstructor
public class CssController {

    private final OpenAIService openAIService;

    @GetMapping
    public ResponseEntity<String> testOpenAI(@RequestParam(required = false) String prompt) {
        if (prompt == null || prompt.trim().isEmpty()) {
            return ResponseEntity.ok("empty");
            // return ResponseEntity.badRequest().body("Prompt parameter is required");
        }

        try {
            String response = openAIService.makeOpenAIRequest("I have a css code that has been written according to the task and the html code, and they are: " + prompt + " Now, you are an exam analyser and you must give me the answer only with these templates," +
                                                                        " If my code fully meets the requirements and it's correct so you must tell me just:" +
                                                                        " \"Your answer is correct!\"" +
                                                                        " If my code does not meet the requirements for the task or it has mistakes in syntax so you must tell me just:" +
                                                                        " \"You answer is incorrect, and the correct code must be ...\" and you must use only HTML character entity references instead of symbols.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
}