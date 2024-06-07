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
@RequestMapping("/validate_help")
@RequiredArgsConstructor
public class HelpController {

    private final OpenAIService openAIService;

    @GetMapping
    public ResponseEntity<String> testOpenAI(@RequestParam(required = false) String prompt) {
        if (prompt == null || prompt.trim().isEmpty()) {
            return ResponseEntity.ok("empty");
            // return ResponseEntity.badRequest().body("Prompt parameter is required");
        }

        try {
            String response = openAIService.makeOpenAIRequest("You are an assistant to give me a little hint to solve the problem, I will give you the html, css, javascript codes I tried. " + prompt +
                                                                        " You should write me a small hint to solve the problem. Do not give the answer directly. Do not write the code yourself." +
                                                                        " I am writing this code inside an online code editor, so I cannot debug and there is no console. Also, consider this as an online exam where I don't have to write everything from scratch. Keep that in mind.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

}