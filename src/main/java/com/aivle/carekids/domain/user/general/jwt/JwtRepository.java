package com.aivle.carekids.domain.user.general.jwt;


import com.aivle.carekids.domain.user.general.jwt.constants.JwtConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class JwtRepository {

    private final RedisTemplate redisTemplate;

    public RefreshToken save(RefreshToken refreshToken) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(refreshToken.getToken(), refreshToken.getUsersId());
        redisTemplate.expire(refreshToken.getToken(), JwtConstants.REFRESH_EXP_TIME, TimeUnit.MILLISECONDS); // 5분 동안 Redis 에 저장
        return refreshToken;
    }

    public Optional<RefreshToken> findByToken(String refreshToken) {
        ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
        Long userId = valueOperations.get(refreshToken);

        if (Objects.isNull(userId)) {
            return Optional.empty();
        }
        return Optional.of(new RefreshToken(refreshToken, userId));
    }
}