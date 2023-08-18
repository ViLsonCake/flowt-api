package project.vilsoncake.Flowt.service;

import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.exception.MinioFileException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface UserManagementService {
    Boolean addUserAvatarByUsername(String authHeader, MultipartFile avatar);
    Boolean changeUserActiveByUsername(String username);
    Long deleteUserByUsername(String username);
}
