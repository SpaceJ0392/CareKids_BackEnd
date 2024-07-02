package com.aivle.carekids.domain.user.general.jwt;


import com.aivle.carekids.domain.user.general.jwt.constants.JwtConstants;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;
import org.springframework.web.util.UriUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class JwtRepository {

    private final RedisTemplate redisTemplate;
    private final RedisConnectionFactory connectionFactory;

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

//    public void DeleteRefreshTokenByUsersId(Long usersId) throws IOException, ClassNotFoundException {
//        redisTemplate.setConnectionFactory(connectionFactory);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//
//        ScanOptions options = ScanOptions.scanOptions().match("*").count(1000).build();
//        Cursor<byte[]> cursor = redisTemplate.getConnectionFactory().getConnection().scan(options);
//
//        // utf-8 인코딩, 디코딩
//        // 직렬화, 역직렬화
//        while (cursor.hasNext()) {
//            if (cursor.next() == null){
//                return;
//            }
//
//            String key;
//
//            try (ByteArrayInputStream bis = new ByteArrayInputStream(cursor.next());
//                ObjectInputStream ois = new ObjectInputStream(bis)){
//                key = (String) ois.readObject();
//            }
//
//            ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
//            Long storedValue = valueOperations.get(key);
//
//            System.out.println(storedValue);
//            if (usersId.equals(storedValue)) {
//                redisTemplate.delete(key);
//                return;
//            }
//        }
//    }

    public void addBlackList(String at) {
        redisTemplate.opsForValue().set(at, -1);
    }

    public Long getValues(String at){
        Long val = (Long) redisTemplate.opsForValue().get(at);
        if (val == null){
            return 1L;
        }
        return val;
    }

}