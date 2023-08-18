package project.vilsoncake.Flowt.service;

import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.exception.MinioFileException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface MinioFileService {
    boolean saveFile(String bucket, String filename, MultipartFile file);
    byte[] getFileContent(String bucket, String filename) throws MinioFileException;
}
