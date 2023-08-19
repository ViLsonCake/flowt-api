package project.vilsoncake.Flowt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.vilsoncake.Flowt.exception.AccountAlreadyVerifiedException;
import project.vilsoncake.Flowt.exception.VerifyCodeNotFoundException;
import project.vilsoncake.Flowt.service.UserVerifyService;

@RestController
@RequestMapping("/verify")
@RequiredArgsConstructor
public class VerifyController {

    private final UserVerifyService userVerifyService;

    @GetMapping
    public ResponseEntity<?> verifyEmail(@RequestParam("code") String code) throws AccountAlreadyVerifiedException, VerifyCodeNotFoundException {
        return ResponseEntity.ok(userVerifyService.verifyUser(code));
    }
}
