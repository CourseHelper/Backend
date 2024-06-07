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
@RequestMapping("/css")
@RequiredArgsConstructor
public class CssController {

    private final OpenAIService openAIService;

    @GetMapping
    public ResponseEntity<String> getCssValidation(@RequestParam(required = false) String prompt) {
        String response = openAIService.validateCss(prompt);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
