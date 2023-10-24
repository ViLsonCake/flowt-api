package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import project.vilsoncake.Flowt.service.RedisService;

import java.util.Random;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final StringRedisTemplate redisTemplate;

    @Override
    public void setValueToCode(String key, String value) {
        redisTemplate.opsForValue().set("codes:" + key, value);
    }

    @Override
    public String getValueFromCode(String key) {
        return redisTemplate.opsForValue().get("codes:" + key);
    }

    @Override
    public void setValueToWarning(String key, String value) {
        redisTemplate.opsForValue().set("warning:" + key, value);
    }

    @Override
    public String getValueFromWarning(String key) {
        return redisTemplate.opsForValue().get("warning:" + key);
    }

    @Override
    public String deleteByKeyFromCode(String key) {
        redisTemplate.delete("codes:" + key);
        return key;
    }

    @Override
    public String deleteByKeyFromWarning(String key) {
        redisTemplate.delete("warning:" + key);
        return key;
    }

    @Override
    public boolean isValidUserCode(String username, Integer code) {
        return getValueFromCode(username).equals(String.valueOf(code));
    }

    @Override
    public String saveNewPasswordCode(String username) {
        String randomCode = String.valueOf(new Random().nextInt(1000, 9999));
        // Delete previous code
        if (getValueFromCode(username) != null) {
            deleteByKeyFromCode(username);
        }

        setValueToCode(username, randomCode);
        return randomCode;
    }

    @Override
    public Set<String> getAllCodeKeys() {
        return redisTemplate.keys("codes:" + "*");
    }

    @Override
    public Set<String> getAllWarningKeys() {
        return redisTemplate.keys("warning:" + "*");
    }
}
