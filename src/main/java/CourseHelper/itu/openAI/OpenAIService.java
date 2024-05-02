package CourseHelper.itu.openAI;

import com.google.common.util.concurrent.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;

@Service
@RequiredArgsConstructor
public class OpenAIService {

    private final RateLimiter rateLimiter = RateLimiter.create(0.5); // 0.5 requests per second
    private final RestTemplate restTemplate;

    @Value("${openai.api.key}")
    private String apiKey;

    public String makeOpenAIRequest(String prompt) {
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
    }
}
