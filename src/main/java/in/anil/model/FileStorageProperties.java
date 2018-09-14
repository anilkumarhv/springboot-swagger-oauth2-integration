package in.anil.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * Created by AH00554631 on 6/15/2018.
 */
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}