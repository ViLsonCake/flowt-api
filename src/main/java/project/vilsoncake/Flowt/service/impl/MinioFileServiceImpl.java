package project.vilsoncake.Flowt.service.impl;

import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.exception.MinioFileException;
import project.vilsoncake.Flowt.service.MinioFileService;

import java.io.InputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class MinioFileServiceImpl implements MinioFileService {

    private final MinioClient minioClient;

    @Override
    public boolean saveFile(String bucket, String filename, MultipartFile file) {
        try {
            // If bucket not exists, create him
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }

            // Create minio object and save
            PutObjectArgs objectArgs = PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(filename)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();

            log.info("File '{}' added to minio storage in bucket '{}'.", filename, bucket);
            minioClient.putObject(objectArgs);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public byte[] getFileContent(String bucket, String filename) throws MinioFileException {
        try {
            InputStream inputStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(filename)
                            .build()
            );
            return inputStream.readAllBytes();
        } catch (Exception e) {
            throw new MinioFileException("Failed to get file.");
        }
    }
}
