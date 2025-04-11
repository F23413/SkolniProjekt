package com.kvadrazicdev.KvadRazic.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AwsS3Sluzba {
    private final String bucketJmeno = "kvadrazic-film-imgs";

    @Value("${aws.s3.access.key}")
    private String awsS3AccessKey;

    @Value("${aws.s3.secret.key}")
    private String awsS3SecretKey;
}
