package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import project.vilsoncake.Flowt.service.RedisService;

import java.util.Random;

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
    public String deleteByKey(String key) {
        redisTemplate.delete(key);
        return key;
    }

    @Override
    public boolean isValidUserCode(String username, Integer code) {
        return getValue(username).equals(String.valueOf(code));
    }

    @Override
    public String saveNewPasswordCode(String username) {
        String randomCode = String.valueOf(new Random().nextInt(1000, 9999));
        // Delete previous code
        if (getValue(username) != null) deleteByKey(username);

        setValue(username, randomCode);
        return randomCode;
    }
}
