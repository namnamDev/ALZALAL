package com.ssafy.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

/* application.yml에 설정한 파일 업로드 경로를 가져옴 */
@ConfigurationProperties(prefix = "file.upload")
public class FileUploadProperties {

    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}