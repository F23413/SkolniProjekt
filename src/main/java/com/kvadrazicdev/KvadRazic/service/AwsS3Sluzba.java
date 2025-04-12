package com.kvadrazicdev.KvadRazic.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kvadrazicdev.KvadRazic.exception.Vyjimka;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class AwsS3Sluzba {
    private final String bucketJmeno = "kvadrazic-film-imgs";

    @Value("${aws.s3.access.key}")
    private String awsS3AccessKey;

    @Value("${aws.s3.secret.key}")
    private String awsS3SecretKey;

    public String ulozImgDoS3(MultipartFile obr){
        String s3LokaceObr = null;

        try {
            String s3NazevSouboru = obr.getOriginalFilename();
            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsS3AccessKey, awsS3SecretKey);
            AmazonS3 s3Klient = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(Regions.EU_NORTH_1)
                    .build();
            InputStream inputStream = obr.getInputStream();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/jpeg");

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketJmeno, s3NazevSouboru, inputStream, metadata);
            s3Klient.putObject(putObjectRequest);
            return "https://" + bucketJmeno + ".s3.amazonaws.com/" +s3NazevSouboru;
        }catch (Exception e){
            e.printStackTrace();
            throw new Vyjimka("Nastala chyba s nahráváním obrázku" + e.getMessage());
        }
    }
}

