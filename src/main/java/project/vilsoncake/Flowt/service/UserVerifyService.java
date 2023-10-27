package project.vilsoncake.Flowt.service;

import project.vilsoncake.Flowt.entity.ReportEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.exception.AccountAlreadyVerifiedException;
import project.vilsoncake.Flowt.exception.VerifyCodeNotFoundException;

import java.util.Map;

public interface UserVerifyService {
    Map<String, String> sendVerifyMailAndNotification(UserEntity user);
    Map<String, String> verifyUserEmail(String code) throws VerifyCodeNotFoundException, AccountAlreadyVerifiedException;
    Map<String, String> sendChangePasswordMessageByUsername(String username);
    Map<String, String> sendChangePasswordMessageByEmail(String email);
    boolean sendWarningMessage(ReportEntity report);
}
