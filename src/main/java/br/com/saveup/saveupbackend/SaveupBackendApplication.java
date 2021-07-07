package br.com.saveup.saveupbackend;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
@SpringBootApplication
@EnableConfigurationProperties(SaveupProperties.class)
public class SaveupBackendApplication {

	private final SaveupProperties saveupProperties;

	public static void main(String[] args) {
		SpringApplication.run(SaveupBackendApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		return modelMapper;
	}

	@Bean
	public AmazonS3 s3Client() {
		AWSCredentials credentials = new BasicAWSCredentials(saveupProperties.getAwsAccessKeyId(),
				saveupProperties.getAwsSecretKey());

		AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_2).build();

		return s3Client;
	}

}
