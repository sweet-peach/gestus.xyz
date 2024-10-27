package xyz.gestus.gestus.feature.preview;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/preview")
public class PreviewController {

    @Value("${ROOT_USER_EMAIL}")
    private String rootUserEmail;

    @Value("${ROOT_USER_PASSWORD}")
    private String rootUserPassword;
    @GetMapping("/credentials")
    public ResponseEntity<Map<String, String>> getCredentials() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", rootUserEmail);
        credentials.put("password", rootUserPassword);

        return new ResponseEntity<>(credentials, HttpStatus.OK);
    }
}