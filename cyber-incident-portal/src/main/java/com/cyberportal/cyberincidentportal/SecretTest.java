package com.cyberportal;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

public class SecretTest {

    public static void main(String[] args) {

        String secretName = "cyber-incident-portal/config";

        try (SecretsManagerClient client = SecretsManagerClient.builder()
                .region(Region.AP_SOUTH_1)
                .build()) {

            GetSecretValueRequest request = GetSecretValueRequest.builder()
                    .secretId(secretName)
                    .build();

            GetSecretValueResponse response = client.getSecretValue(request);

            System.out.println("AWS Secrets Manager connection successful!");
            System.out.println("Secret was found successfully.");

        } catch (Exception e) {
            System.out.println("Could not read the AWS secret.");
            System.out.println("Error: " + e.getMessage());
        }
    }
}
