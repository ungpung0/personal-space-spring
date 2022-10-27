package com.study.movieproject.controller;

import com.study.movieproject.dto.UploadResultDTO;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController // @RestController: @Controller에 @ResponseBody를 추가한 것으로, JSON 데이터를 반환할 때 사용한다.
@Log4j2
public class UploadController {
    @Value("${com.study.upload.path}") // application.properties의 경로값을 갖는 변수.
    private String uploadPath;

    private String makeFolder() {
        // 날짜를 기준으로 폴더명을 결정.
        String folderName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = folderName.replace("/", File.separator); // OS별 디렉토리 구분자가 달라서 File.separator를 사용한다.

        // 폴더가 존재하지 않으면 새 폴더 생성.
        File newFolder = new File(uploadPath, folderPath);
        if(newFolder.exists() == false) {
            newFolder.mkdirs();
        }

        return folderPath;
    }
    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile[] uploadFiles) { // Multipart는 HTTP 바디에 파일을 분할하여 전송하는 데이터.
        // 반환할 DTO를 담고 있는 list 객체.
        List<UploadResultDTO> resultDTOList = new ArrayList<>();

        for(MultipartFile uploadFile : uploadFiles) {
            // 파일 이미지 여부 검사.
            if(uploadFile.getContentType().startsWith("image") == false) {
                log.warn("this file is not image type");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            // Internet Explorer과 Edge 대비. (전체 경로가 들어옴)
            String originalName = uploadFile.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
            log.info("File Name: " + fileName);

            // makeFolder()를 호출하여 폴더를 생성하고, UUID로 중복 파일 등록 방지.
            String folderPath = makeFolder();
            String uuid = UUID.randomUUID().toString();
            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;

            Path savePath = Paths.get(saveName); // Path로 경로를 정의한다.

            // 오류가 발생하지 않으면 파일을 최종 등록.
            try {
                uploadFile.transferTo(savePath);

                // Thumbnailator를 통해 섬네일 생성. (UUID 앞에 's_' 추가)
                String thumnailName = uploadPath + File.separator + folderPath + File.separator +
                        "s_" + uuid + "_" + fileName;
                File thumnailFile = new File(thumnailName);
                Thumbnailator.createThumbnail(savePath.toFile(), thumnailFile, 100, 100);

                resultDTOList.add(new UploadResultDTO(fileName, uuid, folderPath));
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new ResponseEntity<>(resultDTOList, HttpStatus.OK); // ResponseEntity는 응답할 HTTP Body, HTTP Header를 담고 있는 객체.
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName) {
        ResponseEntity<byte[]> result = null;

        try {
            // 파일명을 디코딩하여 변수에 저장한다.
            String srcFileName = URLDecoder.decode(fileName, "UTF-8");
            log.info("FileName: " + srcFileName);

            // 경로를 포함하여 File 객체를 생성한다.
            File file = new File(uploadPath + File.separator + srcFileName);
            log.info("File: " + file);

            /*
                MIME은 클라이언트에게 전송된 문서의 다양성을 알려주기 위한 메커니즘이다.
                MIME 타입의 객체를 Files.probeContentType을 통해서 처리한다.
            */
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Type", Files.probeContentType(file.toPath()));

            // 파일 데이터를 Spring의 FileCopyUtils를 통해서 처리한다.
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), httpHeaders, HttpStatus.OK);
        }catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }

    @PostMapping("/removeFile")
    public ResponseEntity<Boolean> removeFile(String fileName) {
        String srcFileName = null;

        try {
            srcFileName = URLDecoder.decode(fileName, "UTF-8");

            // 파일을 제거하고 결과를 저장한다.
            File file = new File(uploadPath + File.separator + srcFileName);
            boolean result = file.delete();

            // 썸네일을 제거하고 결과를 저장한다.
            File thumnail = new File(file.getParent(), "s_" + file.getName()); // File 객체의 getParent()는 경로를, getName() 이름을 반환한다.
            result = thumnail.delete();

            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
