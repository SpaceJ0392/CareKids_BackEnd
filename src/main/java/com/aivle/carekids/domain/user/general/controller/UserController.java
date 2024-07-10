package com.aivle.carekids.domain.user.general.controller;

import com.aivle.carekids.domain.user.dto.EmailDto;
import com.aivle.carekids.domain.user.dto.SignUpRequestDto;
import com.aivle.carekids.domain.user.dto.UsersDetailDto;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtConstants;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils;
import com.aivle.carekids.domain.user.general.service.EmailService;
import com.aivle.carekids.domain.user.general.service.UsersService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UsersService userService;
    private final EmailService emailService;
    private final JwtUtils jwtUtils;

    // 회원 가입 페이지 입장 API
    @GetMapping("/signup")
    public ResponseEntity<Object> signUp(@RequestParam(required = false) String email,
                                            @RequestParam(value = "social-type", required = false) String socialType){

        return userService.signUp(email, socialType);
    }

    // 회원 가입 시 이메일 인증 API
    @PostMapping("/send-email")
    public ResponseEntity<Map<String, String>> sendEmail(@RequestBody @Valid EmailDto emailDto) throws MessagingException, NoSuchAlgorithmException {
        return emailService.sendEmail(emailDto.getEmail());
    }

    // 이메일 인증 번호 검증 API
    @PostMapping("/auth-email")
    public  ResponseEntity<Map<String, String>> authEmail (@RequestBody EmailDto emailDto) throws MessagingException, NoSuchAlgorithmException {
        return emailService.verifyEmailCode(emailDto.getEmail(), emailDto.getCode());
    }

    // 회원 가입 시 API (회원가입 complete or denied)
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signUpRequest(@RequestBody @Valid SignUpRequestDto signUpData) throws URISyntaxException {
        return userService.signUpRequest(signUpData);
    }

    @GetMapping("/user-detail")
    public ResponseEntity<?> displayUsersDetail(@CookieValue(name = "AccessToken") String accessToken,
                              @CookieValue(name = "RefreshToken") String refreshToken){

        Map<String, String> tokenMaps = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (tokenMaps.get("state") != null) {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근 입니다."));
        }

        HttpHeaders headers = new HttpHeaders();
        if (tokenMaps.get("access_token") != null) {
            headers.add(HttpHeaders.SET_COOKIE, tokenMaps.get("access_token"));
        }

        Long usersId = JwtUtils.getUsersId(JwtUtils.verifyToken(accessToken));
        UsersDetailDto usersDetailDto = userService.displayUsersDetail(usersId);

        if (usersDetailDto == null){
            return ResponseEntity.badRequest().headers(headers).body(Map.of("message", "존재하지 않는 사용자 입니다."));
        }

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(usersDetailDto);
    }


    // jwt 검증이 제대로 되는지 controller에서 확인하기 위한 코드
    @PostMapping("/verify-jwts")
    public ResponseEntity<Map<String, String>> VerifyJwts(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String accessToken = JwtUtils.getAccessTokenFromCookies(cookies);
        String refreshToken = JwtUtils.getRefreshTokenFromCookies(cookies);

        Map<String, String> verify_infos = jwtUtils.verifyJWTs(accessToken, refreshToken);

        if (verify_infos.containsKey("access_cookie")){ // -> 이런 식으로 map의 key 값을 받아와서 쓰시면 됩니다
            Cookie access_cookie = new Cookie(JwtConstants.ACCESS, verify_infos.get("access_cookie"));
            response.addCookie(access_cookie);
        }

        return ResponseEntity.ok().body(verify_infos);
    }
}
