package CourseHelper.itu.openAI;

import com.google.common.util.concurrent.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.HtmlUtils;

@Service
@RequiredArgsConstructor
public class OpenAIService {

    private final RateLimiter rateLimiter = RateLimiter.create(0.5); // 0.5 requests per second
    private final RestTemplate restTemplate;

    @Value("${openai.api.key}")
    private String apiKey;

    public String getExamGrading(String prompt) {
        validatePrompt(prompt);
        String requestMessage = "Grade the following code with an integer number according to the problem, maximum score, html part, css part, and javascript part. " + prompt +
                " Now, you should give partial grade out of total score to the given code. You can be generous." +
                " Write only an integer number without any comments.";
        return makeOpenAIRequest(requestMessage);
    }

    public String getExamQuestion(String prompt) {
        validatePrompt(prompt);
        String requestMessage = "Generate a general problem using the features: " + prompt +
                " You should write a creative question for the user to answer using only given languages to test the capability of the user.";
        return makeOpenAIRequest(requestMessage);
    }

    public String getExamHelp(String prompt) {
        validatePrompt(prompt);
        String requestMessage = "You are an assistant to give me a little hint to solve the problem, I will give you the html, css, javascript codes I tried. " + prompt +
                " You should write me a small hint to solve the problem. Do not give the answer directly. Do not write the code yourself." +
                " I am writing this code inside an online code editor, so I cannot debug and there is no console. Also, consider this as an online exam where I don't have to write everything from scratch. Keep that in mind.";
        return makeOpenAIRequest(requestMessage);
    }

    public String validateHtml(String prompt) {
        validatePrompt(prompt);
        String requestMessage = String.format(
                "I have an html code that has been written according to the task, and they are: %s " +
                        "Now, you are an exam analyser and you must give me the answer only with these templates, " +
                        "If my code fully meets the requirements and it's correct so you must tell me just: " +
                        "\"Your answer is correct!\" " +
                        "If my code does not meet ALL the requirements for the task or it has mistakes in syntax (focus if there are missing closing tags) so you must tell me just: " +
                        "\"Your answer is incorrect, and the correct code must be ...\" " +
                        "and you must use only HTML character entity references instead of symbols.",
                prompt
        );
        return makeOpenAIRequest(requestMessage);
    }
    public String validateCss(String prompt) {
        validatePrompt(prompt);
        String requestMessage = String.format(
                "I have a css code that has been written according to the task and the html code, and they are: %s " +
                        "Now, you are an exam analyser and you must give me the answer only with these templates," +
                        "If my code fully meets the requirements and it's correct so you must tell me just:" +
                        "\"Your answer is correct!\"" +
                        "If my code does not meet the requirements for the task or it has mistakes in syntax so you must tell me just:" +
                        "\"You answer is incorrect, and the correct code must be ...\" ",
                prompt
        );
        return makeOpenAIRequest(requestMessage);
    }
    

    private String makeOpenAIRequest(String prompt) {
        try {
            rateLimiter.acquire();
            prompt = HtmlUtils.htmlEscape(prompt);

            String url = "https://api.openai.com/v1/engines/gpt-3.5-turbo-instruct/completions";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("Authorization", "Bearer " + apiKey);

            String requestBody = "{\"prompt\":\"" + prompt + "\",\"max_tokens\":100}";

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(requestBody, headers),
                    String.class
            );

            return response.getBody();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage(), e);
        }
    }

    private void validatePrompt(String prompt) {
        if (prompt == null || prompt.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prompt is empty");
        }
    }
}
