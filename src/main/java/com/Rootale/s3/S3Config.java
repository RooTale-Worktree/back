package com.Rootale.s3;

import io.awspring.cloud.s3.S3Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Slf4j
@Configuration
public class S3Config {

    @Value("${spring.cloud.aws.region.static}")
    private String awsRegion;

    @Value("${spring.cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${spring.cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${app.s3.bucket}")
    private String bucketName;

    @Bean
    public S3Client s3Client() {
        log.info("🔧 Configuring S3Client");
        log.info("   Region: {}", awsRegion);
        log.info("   Bucket: {}", bucketName);
        log.info("   Access Key: {}***", accessKey != null ? accessKey.substring(0, Math.min(4, accessKey.length())) : "null");

        S3Client client = S3Client.builder()
                .region(Region.of(awsRegion))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)
                ))
                .build();

        log.info("✅ S3Client configured successfully");
        return client;
    }

    @Bean
    public S3Presigner s3Presigner() {
        log.info("🔧 Configuring S3Presigner with region: {}", awsRegion);

        return S3Presigner.builder()
                .region(Region.of(awsRegion))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)
                ))
                .build();
    }

    //S#Template: S3Client 정의시 자동생성
}