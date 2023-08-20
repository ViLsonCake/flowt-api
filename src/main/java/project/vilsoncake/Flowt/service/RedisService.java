package project.vilsoncake.Flowt.service;

public interface RedisService {

    void setValue(String key, String value);
    String getValue(String key);
}
