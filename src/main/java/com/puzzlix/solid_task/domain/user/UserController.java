package com.puzzlix.solid_task.domain.user;

import com.puzzlix.solid_task._global.config.jwt.JwtTokenProvider;
import com.puzzlix.solid_task._global.dto.CommonResponseDto;
import com.puzzlix.solid_task.domain.user.dto.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto<?>> signUp(@Valid @RequestBody UserRequest.Signup request){

        userService.signUp(request);
        return ResponseEntity.ok(CommonResponseDto.success(null, "회원가입이 완료되었습니다"));
    }
    // 자원 요청 --> GET
    // 자원 생성 --> POST
    // 자원 수정 --> PUT, Patch
    // 자원 삭제 --> DELETE

    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto<?>> login(@Valid @RequestBody UserRequest.Login request){
        User user = userService.login(request);

        // 사용자 이메일을 기반으로 JWT 토큰 생성
        String token = jwtTokenProvider.createToken(user.getEmail());

        return ResponseEntity.ok(CommonResponseDto.success(token, "로그인에 성공 했습니다"));
    }
}
