package com.aivle.carekids.domain.user.general.jwt.constants;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtConstants {
    public static String SECRET_KEY;
    public static final long ACCESS_EXP_TIME = 60000 * 100;   // 100 분 설정
    public static final long REFRESH_EXP_TIME = 60000 * 120;   // 120 분 설정


    public static final String JWT_HEADER = "Authorization";
    public static final String JWT_TYPE = "BEARER ";

    public static final String ACCESS = "AccessToken";
    public static final String REFRESH = "RefreshToken";


    @Value("${spring.jwt.secret}")
    public void setSecretKey(String SECRET_KEY){
        this.SECRET_KEY = SECRET_KEY;
    }

}