package project.vilsoncake.Flowt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.vilsoncake.Flowt.dto.ChangeAuthorityDto;
import project.vilsoncake.Flowt.dto.UserDto;
import project.vilsoncake.Flowt.service.ChangeUserService;
import project.vilsoncake.Flowt.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ChangeUserService changeUserService;
    private final UserService userService;

    @GetMapping("/user/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.getUserDtoByUsername(username));
    }

    @PatchMapping("/user/{username}")
    public ResponseEntity<ChangeAuthorityDto> changeUserAuthority(@PathVariable("username") String username) {
        return ResponseEntity.ok(changeUserService.changeUserAuthority(username));
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.deleteUser(username));
    }
}
