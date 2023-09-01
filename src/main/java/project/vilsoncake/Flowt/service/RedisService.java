package project.vilsoncake.Flowt.service;

import java.util.Set;

public interface RedisService {

    void setValueToCode(String key, String value);
    String getValueFromCode(String key);
    void setValueToWarning(String key, String value);
    String getValueFromWarning(String key);
    String deleteByKeyFromCode(String key);
    String deleteByKeyFromWarning(String key);
    boolean isValidUserCode(String username, Integer code);
    String saveNewPasswordCode(String username);
    Set<String> getAllCodeKeys();
    Set<String> getAllWarningKeys();
}
