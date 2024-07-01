package com.aivle.carekids.domain.user.general.jwt.constants;


import com.aivle.carekids.domain.common.models.AgeTag;
import com.aivle.carekids.domain.user.models.Kids;
import com.aivle.carekids.domain.user.models.Users;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional(readOnly=true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUtils {


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
//                .withClaim("region", users.getRegion().getRegionName())
//                .withClaim("kids-age", kidsAge)
                .withExpiresAt(createExpireDate(JwtConstants.ACCESS_EXP_TIME))
                .sign(Algorithm.HMAC512(JwtConstants.SECRET_KEY));
    }

    // Refresh Token 에는 아무것도 담지 않는다
    public static String generateRefreshToken(Users users) {
        return JWT.create()
                .withSubject(JwtConstants.REFRESH)
                .withHeader(createHeader())
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


    // 토큰 유효성 검사 => 여러 예외가 발생함 ( 호출하는 곳에서 처리 필요 )
    public static DecodedJWT verifyToken(String token) {
        return JWT.require(Algorithm.HMAC512(JwtConstants.SECRET_KEY)).build().verify(token);
    }

    public static UsernamePasswordAuthenticationToken getAuthenticationToken(DecodedJWT decodedJWT) {
        String id = decodedJWT.getClaim("id").asString();
        String role = decodedJWT.getClaim("role").asString();
        String region = decodedJWT.getClaim("region").asString();
        String kidsAge = decodedJWT.getClaim("kids-age").asString();

        System.out.println("토큰에서 얻은 사용자 정보");
        System.out.println(id + role + region + kidsAge);

        return new UsernamePasswordAuthenticationToken(id, null,
                Collections.singleton(new SimpleGrantedAuthority(role)));
    }
}