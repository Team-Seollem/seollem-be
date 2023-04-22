package com.seollem.server.file;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.seollem.server.exception.BusinessLogicException;
import com.seollem.server.exception.ExceptionCode;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
@Slf4j
public class FileUploadService {

  private final AmazonS3Service s3Service;

  public String createImage(MultipartFile file) {
    String fileName = createFileName(file.getOriginalFilename());
    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentLength(file.getSize());
    objectMetadata.setContentType(file.getContentType());
    try (InputStream inputStream = file.getInputStream()) {
      s3Service.uploadFile(inputStream, objectMetadata, fileName);
    } catch (IOException e) {
      log.error(String.format("파일 변환 중 에러 발생 (%s)", file.getOriginalFilename()));
      throw new BusinessLogicException(ExceptionCode.IMAGE_UPLOAD_FAIL);
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
