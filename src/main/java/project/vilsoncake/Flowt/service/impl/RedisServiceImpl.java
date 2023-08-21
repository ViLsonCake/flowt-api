package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import project.vilsoncake.Flowt.service.RedisService;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final StringRedisTemplate redisTemplate;

    @Override
    public void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean deleteByKey(String key) {
        redisTemplate.delete(key);
        return true;
    }

    @Override
    public boolean isValidUserCode(String username, Integer code) {
        return getValue(username).equals(String.valueOf(code));
    }
}
