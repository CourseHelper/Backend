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
                " If the problem does not need an html code and the user correctly completed other parts, you can give full marks; and same for other cases. Give zero points if the answer is empty or incorrect, give maximum score if the answer is fully correct." +
                " Write only an integer number without any comments. Be extremely stingy when grading the code, check if the code meets what the problem wants." +
                " If the answer is correct, give maximum score.";
        return makeOpenAIRequest(requestMessage);
    }

    public String getExamReview(String prompt) {
        validatePrompt(prompt);
        String requestMessage = "Review and correct the following code according to the problem: " + prompt;
        return makeOpenAIRequest(requestMessage);
    }

    public String getExamQuestion(String prompt) {
        validatePrompt(prompt);
        String requestMessage = "Generate general problems using the features: " + prompt +
                " You should write creative and different problems for the user to answer using only given languages to test the capability of the user." +
                " Separate the problems only with @ sign.";
        return makeOpenAIRequest(requestMessage);
    }
//    public List<String> generateQuestions() {
//        String prompt = "Generate 4 unique and challenging coding problems related to HTML, CSS, and JavaScript.";
//        String response = makeOpenAIRequest(prompt);
//        // Extract the 4 questions from the response
//        String[] questions = response.split("\n");
//        return Arrays.asList(questions);
//    }
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
                "there is a task and the answer: %s The answer has been written according to the task" +
                        "Now, you are an exam analyser and you must check the given answer and it must be html code and  give me the answer only with these templates, " +
                        "If the answer is html code and fully meets the requirements and it's correct so you must tell me just: " +
                        "\"Your answer is correct!\" " +
                        "If the answer does not meet ALL the requirements for the task or if it has mistakes in syntax (focus if there are missing closing tags) so you must tell me just: " +
                        "\"Your answer is incorrect, and the correct code must be ...\" " +
                        "In your response if there's corrected code, you must follow the following format in your response &lt;h1&gt;xxxxx&lt;/h1&gt; and change h1 according to the given task",
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

    public String validateJs(String prompt) {
        validatePrompt(prompt);
        String requestMessage = String.format(
                "I have a javascript code that has been written according to the task, In the task the css code and the html code are given" +
                        "Task, html, css, and js are: %s " +
                        "Now, you are an exam analyser and you must give me the answer only with these templates for code evaluation," +
                        "1- If my js code fully meets the requirements and it's correct so you must tell me just:" +
                        "\"Your answer is correct!\"" +
                        "2- If my js code does not meet the requirements (the answer should be answered according to the task) or if it has mistakes in syntax so you must tell me and you have to mention this:" +
                        "\"You answer is incorrect, and the correct code must be ...\" " +
                        "Correct only the javascript code according to the given instructions if the answer doesn't implement the task correctly. Write only the javascript part of the corrected code." +
                        "In your response DO NOT FORGET TO FOLLOW THESE: {Your answer is correct! OR You answer is incorrect, ...}, corrected javascript code, no comments, no other sentences.",
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
            // throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prompt is empty");
        }
    }
}
