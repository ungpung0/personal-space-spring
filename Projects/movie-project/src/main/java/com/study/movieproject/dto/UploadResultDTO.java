package com.study.movieproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
public class UploadResultDTO implements Serializable { // Serializable은 객체를 바이트 형태로 변환 직렬화를 구현할 때 사용한다.
    private String fileName;
    private String uuid;
    private String folderPath;

    // 파일의 '전체 경로(폴더/UUID_파일명)'를 반환하는 메소드.
    public String getImageURL() {
        try {
            return URLEncoder.encode(folderPath + "/" + uuid + "_" + fileName, "UTF-8");
        }catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    //  썸네일의 전체 경로를 반환하는 메소드.
    public String getThumbnailURL() {
        try {
            return URLEncoder.encode(folderPath + "/" + "s_" + uuid + "_" + fileName, "UTF-8");
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }


}
