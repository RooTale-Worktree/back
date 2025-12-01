package com.Rootale.s3;

import io.awspring.cloud.s3.S3Template;
import io.awspring.cloud.s3.S3Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.*;
import java.net.URL;
import java.time.*;
import java.util.List;
import java.util.stream.Collectors;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3FileService {

    private final S3Template s3Template;
    private final S3Presigner s3Presigner;
    private final S3Client s3Client;

    @Value("${spring.cloud.aws.region.static}")
    private String awsRegion;

    @Value("${app.s3.bucket}")
    private String bucketName;

    /**
     * S3Client를 직접 사용한 업로드 (오류log 확인가능)
     */
    public String upload(String bucket, String key, MultipartFile file) throws Exception {
        try {
            log.info("📤 Starting S3 upload (using S3Client directly)");
            log.info("   Region: {}", awsRegion);
            log.info("   Bucket: {}", bucket);
            log.info("   Key: {}", key);
            log.info("   File: {} ({} bytes, {})",
                    file.getOriginalFilename(),
                    file.getSize(),
                    file.getContentType());

            // ⭐ S3Client를 직접 사용 (더 명확한 에러 메시지)
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            log.info("✅ S3 upload successful - key: {}", key);
            return key;

        } catch (S3Exception e) {
            log.error("❌ S3 upload failed (AWS S3 Error)!");
            log.error("   Region: {}", awsRegion);
            log.error("   Bucket: {}", bucket);
            log.error("   Key: {}", key);
            log.error("   AWS Error Code: {}", e.awsErrorDetails().errorCode());
            log.error("   AWS Error Message: {}", e.awsErrorDetails().errorMessage());
            log.error("   Status Code: {}", e.statusCode());

            throw new RuntimeException("S3 업로드 실패: " + e.awsErrorDetails().errorMessage(), e);

        } catch (Exception e) {
            log.error("❌ S3 upload failed!");
            log.error("   Region: {}", awsRegion);
            log.error("   Bucket: {}", bucket);
            log.error("   Key: {}", key);
            log.error("   Error: {}", e.getMessage());
            log.error("   Error Type: {}", e.getClass().getName());

            if (e.getCause() != null) {
                log.error("   Cause: {}", e.getCause().getMessage());
            }

            throw new RuntimeException("S3 업로드 실패: " + e.getMessage(), e);
        }
    }

    public InputStreamResource download(String bucket, String key) throws IOException {
        S3Resource s3Res = s3Template.download(bucket, key);
        return new InputStreamResource(s3Res.getInputStream());
    }

    public void delete(String bucket, String key) {
        s3Template.deleteObject(bucket, key);
    }

    public List<String> list(String bucket, String prefix) {
        String p = (prefix == null) ? "" : prefix;
        return s3Template.listObjects(bucket, p)
                .stream()
                .map(res -> res.getLocation().getObject())
                .collect(Collectors.toList());
    }

    public URL presignGet(String bucket, String key, Duration expiresIn) {
        try {
            GetObjectRequest getReq = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();
            GetObjectPresignRequest presignReq = GetObjectPresignRequest.builder()
                    .signatureDuration(expiresIn)
                    .getObjectRequest(getReq)
                    .build();
            URL url = s3Presigner.presignGetObject(presignReq).url();
            log.debug("✅ Presigned GET URL generated for key: {}", key);
            return url;
        } catch (Exception e) {
            log.error("❌ Failed to generate presigned URL for key: {}", key, e);
            throw e;
        }
    }

    public URL presignPut(String bucket, String key, Duration expiresIn, String contentType) {
        PutObjectRequest putReq = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                .build();
        PutObjectPresignRequest presignReq = PutObjectPresignRequest.builder()
                .signatureDuration(expiresIn)
                .putObjectRequest(putReq)
                .build();
        return s3Presigner.presignPutObject(presignReq).url();
    }

    public void uploadWithMeta(String bucket, String key, MultipartFile file, String contentType) {
        try {
            String ct = contentType != null ? contentType : MediaType.APPLICATION_OCTET_STREAM_VALUE;

            log.info("📤 Uploading with metadata - bucket: {}, key: {}, contentType: {}", bucket, key, ct);

            PutObjectRequest req = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(ct)
                    .contentLength(file.getSize())
                    .build();

            s3Client.putObject(req,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            log.info("✅ Upload with metadata successful - key: {}", key);
        } catch (S3Exception e) {
            log.error("❌ Upload failed - AWS Error: {}", e.awsErrorDetails().errorMessage());
            throw new RuntimeException("S3 upload failed: " + e.awsErrorDetails().errorMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("File read error: " + e.getMessage(), e);
        }
    }

    public void uploadWithTemplateMeta(String bucket, String key, MultipartFile file, String contentType) {
        try {
            String ct = contentType != null ? contentType : MediaType.APPLICATION_OCTET_STREAM_VALUE;

            log.info("📤 Uploading with S3Template - bucket: {}, key: {}, contentType: {}", bucket, key, ct);

            io.awspring.cloud.s3.ObjectMetadata metadata = io.awspring.cloud.s3.ObjectMetadata.builder()
                    .contentType(ct)
                    .build();
            s3Template.upload(bucket, key, file.getInputStream(), metadata);

            log.info("✅ S3Template upload successful - key: {}", key);
        } catch (IOException e) {
            throw new RuntimeException("S3 upload with metadata failed", e);
        }
    }
}