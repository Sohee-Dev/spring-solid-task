package com.puzzlix.solid_task.domain.user;

import com.puzzlix.solid_task.domain.user.dto.UserRequest;
import com.puzzlix.solid_task.domain.user.login.LoginStrategy;
import com.puzzlix.solid_task.domain.user.login.LoginStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor // DI 처리
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoginStrategyFactory loginStrategyFactory;
    // AppConfig 에 Bean 으로 등록된 객체를 가져 온다.

    /**
     * 회원 가입
     * */
    // 1. 중복 이메일 확인
    // 2. 사용자가 비밀번호를 암호화 처리
    // 3. DB 저장
    public User signUp(UserRequest.Signup request){
        // 이메일 중복 확인
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 사용 중인 이메일 입니다");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(encodedPassword);

        return userRepository.save(newUser);
    }

    /**
     * 로그인 로직
     * @param request
     * @return
     * */
    @Transactional(readOnly = true)
    public User login(String type, UserRequest.Login request){
        // 1. 팩토리에게 알맞은 로그인 전략을 요청
        LoginStrategy strategy = loginStrategyFactory.findStrategy(type);
        // 2. 해당 전략 클래스를 선택하여 로그인 요청 완료
        return strategy.login(request);
    }

}
