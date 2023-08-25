package project.vilsoncake.Flowt.service;

public interface RedisService {

    void setValue(String key, String value);
    String getValue(String key);
    boolean deleteByKey(String key);
    boolean isValidUserCode(String username, Integer code);
    String saveNewPasswordCode(String username);
}
