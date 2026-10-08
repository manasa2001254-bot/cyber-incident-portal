package com.cyberportal;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;

public class S3Test {

    public static void main(String[] args) {

        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(AwsConfig.class)) {

            S3Client s3Client = context.getBean(S3Client.class);

            s3Client.headBucket(HeadBucketRequest.builder()
                    .bucket("cyber-incident-portal-evidence-2026")
                    .build());

            System.out.println("Java S3 connection successful!");
            System.out.println("S3 bucket is accessible.");

        } catch (Exception e) {
            System.out.println("S3 connection failed.");
            System.out.println("Error: " + e.getMessage());
        }
    }
}
