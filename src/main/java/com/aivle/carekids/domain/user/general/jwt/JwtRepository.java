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
        valueOperations.set(refreshToken.getUsersId(), refreshToken.getToken());
        redisTemplate.expire(refreshToken.getToken(), JwtConstants.REFRESH_EXP_TIME, TimeUnit.MILLISECONDS); // 5분 동안 Redis 에 저장
        return refreshToken;
    }

    public Optional<RefreshToken> findRefreshTokenByUsersId(Long usersId) {
        ValueOperations<Long, String> valueOperations = redisTemplate.opsForValue();
        String refreshToken = valueOperations.get(usersId);

        if (Objects.isNull(usersId)) {
            return Optional.empty();
        }
        return Optional.of(new RefreshToken(usersId, refreshToken));
    }

    public void DeleteRefreshToken(Long usersId) {
        redisTemplate.delete(usersId);
    }


    public void addBlackList(String at) {
        redisTemplate.opsForValue().set(at, -1);
    }

    public Integer getValues(String at){
        Integer val = (Integer) redisTemplate.opsForValue().get(at);
        if (val == null){
            return 1;
        }
        return val;
    }

}