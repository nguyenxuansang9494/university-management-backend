package uit.edu.vn.universitymanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/demo")
public class DemoSecurityController {

    @GetMapping("/hello-world")
    public ResponseEntity<String> helloWorld(Principal principal) {
        return ResponseEntity.ok("Hello, " + principal.getName());
    }
}
