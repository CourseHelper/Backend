package CourseHelper.itu.openAI;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final OpenAIService openAIService;

    @GetMapping
    public String testOpenAI() {
        String prompt = "Provide HTML code for a sign-in form with email and password fields";
        String generatedHtml = openAIService.makeOpenAIRequest(prompt);

        return HtmlUtils.htmlEscape(generatedHtml);
    }
}
