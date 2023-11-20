package project.vilsoncake.Flowt.constant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UrlConst {
    public static final String APP_URL = "http://localhost:8080";
    public static final String USERS_AVATAR_URL = APP_URL + "/images/user/avatar/";
    public static final String FACEBOOK_QUERY_FIELDS_VALUE = "id,name,email";
}
