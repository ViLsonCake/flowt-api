package project.vilsoncake.Flowt.service;

import project.vilsoncake.Flowt.entity.ReportEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.exception.AccountAlreadyVerifiedException;
import project.vilsoncake.Flowt.exception.VerifyCodeNotFoundException;

import java.io.IOException;
import java.util.Map;

public interface UserVerifyService {
    Map<String, String> sendVerifyMailAndNotification(UserEntity user) throws IOException;
    Map<String, String> verifyUserEmail(String code) throws VerifyCodeNotFoundException, AccountAlreadyVerifiedException;
    Map<String, String> sendChangePasswordMessageByUsername(String username) throws IOException;
    Map<String, String> sendChangePasswordMessageByEmail(String email) throws IOException;
    boolean sendWarningMessage(ReportEntity report) throws IOException;
}
