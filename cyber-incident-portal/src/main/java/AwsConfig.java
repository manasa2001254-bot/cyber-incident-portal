package com.cyberportal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sesv2.SesV2Client;

@Configuration
public class AwsConfig {

    private static final Region AWS_REGION = Region.AP_SOUTH_1;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(AWS_REGION)
                .build();
    }

    @Bean
    public SesV2Client sesV2Client() {
        return SesV2Client.builder()
                .region(AWS_REGION)
                .build();
    }
}

