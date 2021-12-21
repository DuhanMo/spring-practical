package com.example.exception.controller;

import com.example.exception.dto.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/api/user")
@Validated // GetMapping에서 RequestParam에 Valid를 걸어주기 위함
public class ApiController {

    @GetMapping
    public User get(
            @Size(min = 2, max = 10)
            @RequestParam String name,

            @NotNull
            @Min(1)
            @RequestParam Integer age) {
        User user = new User();
        user.setName(name);
        user.setAge(age);
        return user;
    }

    @PostMapping
    public User post(@Valid @RequestBody User user) {
        System.out.println(user);
        return user;
    }

    /*
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException e) {
        System.out.println("컨트롤러 내부에 작성하면 해당 컨트롤러에서 발생하는 에러만 잡는다");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }*/
}
