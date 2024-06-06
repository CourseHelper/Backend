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
@RequestMapping("/validate_js")
@RequiredArgsConstructor
public class JsController {

    private final OpenAIService openAIService;

    @GetMapping
    public ResponseEntity<String> testOpenAI(@RequestParam(required = false) String prompt) {
        if (prompt == null || prompt.trim().isEmpty()) {
            return ResponseEntity.ok("empty");
            // return ResponseEntity.badRequest().body("Prompt parameter is required");
        }

        try {
            String response = openAIService.makeOpenAIRequest("I have a javascript code that has been written according to the task, the css code, and the html code, and they are: " + prompt + " Now, you are an exam analyser and you must give me the answer only with these templates for code evaluation," +
                                                                        " 1- If my code fully meets the requirements and it's correct so you must tell me just:" +
                                                                        " \"Your answer is correct!\"" +
                                                                        " 2- If my code does not meet the requirements (you have to be stubborn and normative with the task) for the task or it has mistakes in syntax so you must tell me and you have to mention this:" +
                                                                        " \"You answer is incorrect, and the correct code must be ...\" and you must use only HTML character entity references instead of symbols (do not mention this in your response)." +
                                                                        " Correct the javascript code according to the given instructions if the answer doesn't implement the task correctly. Write only the javascript part of the corrected code." +
                                                                        " DO NOT FORGET TO FOLLOW THESE: {Your answer is correct! OR You answer is incorrect, ...}, corrected javascript code, no comments, no other sentences.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
}
