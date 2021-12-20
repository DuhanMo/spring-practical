package practical.aop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import practical.aop.annotation.Decode;
import practical.aop.annotation.Timer;
import practical.aop.dto.User;

@Slf4j
@RestController
@RequestMapping("/api")
public class RestApiController {

    @GetMapping("/get/{id}")
    public String get(@PathVariable Long id, @RequestParam String name) {
        return id + " " + name;
    }

    @PostMapping("/post")
    public User post(@RequestBody User user) {
        return user;
    }

    @Timer
    @DeleteMapping("/delete")
    public void delete() throws InterruptedException {
        // db logic
        Thread.sleep(2000); // 스레드가 Non-runnable 상태로 진입
    }

    @Decode
    @PutMapping("/put")
    public User put(@RequestBody User user) {
        log.info("decodeEmailSet user={}", user);
        return user;
    }
}
