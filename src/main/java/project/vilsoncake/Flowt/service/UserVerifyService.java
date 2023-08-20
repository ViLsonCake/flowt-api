package project.vilsoncake.Flowt.service;

import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.exception.AccountAlreadyVerifiedException;
import project.vilsoncake.Flowt.exception.VerifyCodeNotFoundException;

public interface UserVerifyService {
    boolean saveAndSendNewCode(UserEntity user);
    boolean verifyUser(String code) throws VerifyCodeNotFoundException, AccountAlreadyVerifiedException;
    boolean sendChangePasswordMessage(UserEntity user);
    boolean changeUserPasswordByUsername(String username, String password);
}
