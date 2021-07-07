package br.com.saveup.saveupbackend;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "saveup")
public class SaveupProperties {

    private String awsAccessKeyId;
    private String awsSecretKey;
    private String bucketName;
    private String originPermitida;
}
