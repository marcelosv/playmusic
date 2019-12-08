package com.marcelsouzav.udemy.music.upload.service.gateway.json;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MusicUploadJson {
    private String uuid;
    private String uuidMusic;
    private MultipartFile file;
}