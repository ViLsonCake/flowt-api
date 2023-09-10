package project.vilsoncake.Flowt.service;

import project.vilsoncake.Flowt.dto.RestorePasswordResponse;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.exception.AccountAlreadyVerifiedException;
import project.vilsoncake.Flowt.exception.VerifyCodeNotFoundException;

import java.util.Map;

public interface UserVerifyService {
    Map<String, String> saveAndSendNewCode(UserEntity user);
    Map<String, String> verifyUser(String code) throws VerifyCodeNotFoundException, AccountAlreadyVerifiedException;
    Map<String, String> sendChangePasswordMessageByUsername(String username);
    Map<String, String> sendChangePasswordMessageByEmail(String email);
    Map<String, String> sendWarningMessage(String username);
}
