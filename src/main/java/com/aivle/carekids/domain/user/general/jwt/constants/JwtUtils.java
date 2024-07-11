package com.aivle.carekids.domain.user.general.jwt.constants;


import com.aivle.carekids.domain.user.general.jwt.JwtRepository;
import com.aivle.carekids.domain.user.general.jwt.RefreshToken;
import com.aivle.carekids.domain.user.models.Users;
import com.aivle.carekids.domain.user.repository.UsersRepository;
import com.aivle.carekids.global.exception.BusinessLogicException;
import com.aivle.carekids.global.exception.ExceptionCode;
import com.aivle.carekids.global.exception.UserNotFoundException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import jakarta.servlet.http.Cookie;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@Transactional(readOnly=true)
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class JwtUtils {
    private final UsersRepository usersRepository;
    private final JwtRepository jwtRepository;

    // Access Token 에는 id 와 role 을 담는다
    public static String generateAccessToken(Users users) {

        // 권한들 가져오기
//        String kidsAge = users.getKids().stream()
//                .map(Kids::getAgeTag)
//                .map(AgeTag::getAgeTagName).collect(
//                        Collectors.joining(", "));

        return JWT.create()
                .withSubject(JwtConstants.ACCESS)
                .withHeader(createHeader())
                .withClaim("id", users.getUsersId())
                .withClaim("role", users.getUsersRole().getRole())
                .withExpiresAt(createExpireDate(JwtConstants.ACCESS_EXP_TIME))
                .sign(Algorithm.HMAC512(JwtConstants.SECRET_KEY));
    }

    public static boolean isTokenExpired(String token) {
        try {
            DecodedJWT jwt = verifyToken(token);
            Date expiration = jwt.getExpiresAt();
            return expiration.before(new Date());
        } catch (JWTDecodeException e) {
            // 토큰이 디코딩되지 않으면 만료된 것으로 간주할 수 있습니다.
            return true;
        }
    }


    // Refresh Token 에는 아무것도 담지 않는다
    public static String generateRefreshToken(Users users) {
        return JWT.create()
                .withSubject(JwtConstants.REFRESH)
                .withHeader(createHeader())
                .withClaim("id", users.getUsersId())
                .withExpiresAt(createExpireDate(JwtConstants.REFRESH_EXP_TIME))
                .sign(Algorithm.HMAC512(JwtConstants.SECRET_KEY));
    }

    // 헤더 정보 생성
    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS512");
        return header;
    }

    // 만료 시간 생성
    private static Date createExpireDate(long expireTime) {
        return new Date(System.currentTimeMillis() + expireTime);
    }


    // 헤더에 Bearer XXX 형식으로 담겨온 토큰을 추출한다
    public static String getTokenFromHeader(String header) {
        return header.split(" ")[1];
    }

    // request.getCookies() 형식으로 전달 된 cookies
    public static String getAccessTokenFromCookies(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("AccessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    // request.getCookies() 형식으로 전달 된 cookies
    public static String getRefreshTokenFromCookies(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("RefreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }


    // 토큰 유효성 검사 => 여러 예외가 발생함 ( 호출하는 곳에서 처리 필요 )
    public static DecodedJWT verifyToken(String token) {
        return JWT.require(Algorithm.HMAC512(JwtConstants.SECRET_KEY)).build().verify(token);
    }

    public static UsernamePasswordAuthenticationToken getAuthenticationToken(DecodedJWT decodedJWT) {
        Long id = decodedJWT.getClaim("id").asLong();
        String role = decodedJWT.getClaim("role").asString();

        return new UsernamePasswordAuthenticationToken(id, null,
                Collections.singleton(new SimpleGrantedAuthority(role)));
    }

    public static Long getUsersId(DecodedJWT decodedJWT){
        return decodedJWT.getClaim("id").asLong();
    }

    public static String getUsersRole(DecodedJWT decodedJWT){
        return decodedJWT.getClaim("role").asString();
    }

    private boolean isLogout(String accessToken) {
        Integer isLogout = jwtRepository.getValues(accessToken);

        return isLogout == -1;
    }

    public Map<String, String> verifyJWTs(String accessToken, String refreshToken){
        Map<String, String> verify_infos = new HashMap<>();

        String role = verifyToken(accessToken).getClaim("role").asString();
        verify_infos.put("role", role);

        // token이 null이거나, 로그아웃된 사용자인 경우
        if (accessToken == null || isLogout(accessToken)){
            verify_infos.put("message", "토큰이 없거나, 로그아웃된 사용자입니다.");
            verify_infos.put("state", "false");
            return verify_infos;
        }

        // 토큰이 만료된 경우 재발급
        if (isTokenExpired(accessToken)){
            Cookie access_cookie = renewToken(refreshToken);
            verify_infos.put("message", "액세스 토큰이 만료되어, 재발급된 사용자입니다.");
            verify_infos.put("access_token", String.valueOf(access_cookie));

            // Controller에서 response.addCookie(access_cookie); 필요
        }

        return verify_infos;
    }

    public Cookie renewToken(String refreshToken) {
        String accessToken = renew(refreshToken);

        Cookie access_cookie = new Cookie(JwtConstants.ACCESS, accessToken);
        access_cookie.setMaxAge((int) (JwtConstants.ACCESS_EXP_TIME / 1000));     // 5분 설정
        access_cookie.setHttpOnly(true);

        return access_cookie;
    }

    public String renew(String refreshToken) {
        // RefreshToken 안의 usersId 를 가져와서 user 를 찾은 후 AccessToken 생성
        Long usersId = JwtUtils.getUsersId(JwtUtils.verifyToken(refreshToken));
        RefreshToken redisRefreshToken = jwtRepository.findRefreshTokenByUsersId(usersId).orElseThrow(() -> new NullPointerException("Refresh Token이 없습니다. 재로그인해주세요."));
        if (Objects.equals(redisRefreshToken.getToken(), refreshToken)){
            Users users = usersRepository.findByUsersId(usersId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다"));
            return JwtUtils.generateAccessToken(users);
        }
        else throw new BusinessLogicException(ExceptionCode.TOKEN_IS_NOT_SAME);
    }
}