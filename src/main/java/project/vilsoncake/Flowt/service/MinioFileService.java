package project.vilsoncake.Flowt.service;

import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.exception.MinioFileException;

public interface MinioFileService {
    boolean saveFile(String bucket, String filename, MultipartFile file);
    boolean removeFile(String bucket, String filename);
    byte[] getFileContent(String bucket, String filename) throws MinioFileException;
}
