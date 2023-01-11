package com.seollem.server.file;

import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class FileUploadService {

  private final AmazonS3Service s3Service;

  public String createImageMemo(MultipartFile file) {
    String fileName = createFileName(file.getOriginalFilename());
    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentLength(file.getSize());
    objectMetadata.setContentType(file.getContentType());
    try (InputStream inputStream = file.getInputStream()) {
      s3Service.uploadFile(inputStream, objectMetadata, fileName);
    } catch (IOException e) {
      throw new IllegalArgumentException(
          String.format("파일 변환 중 에러 발생 (%s)", file.getOriginalFilename()));
    }
    return s3Service.getFileUrl(fileName);
  }

  private String createFileName(String originalFileName) {
    return UUID.randomUUID().toString().concat(getFileExtension(originalFileName));
  }

  private String getFileExtension(String fileName) {
    try {
      return fileName.substring(fileName.lastIndexOf("."));
    } catch (StringIndexOutOfBoundsException e) {
      throw new IllegalArgumentException(String.format("잘못된 형식의 파일 (%s) 입니다.", fileName));
    }
  }
}
