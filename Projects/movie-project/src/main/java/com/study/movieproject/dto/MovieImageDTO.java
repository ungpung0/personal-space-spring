package com.study.movieproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieImageDTO {
    private String uuid;
    private String imgName;
    private String imgPath;

    public String getImageURL() {
        try {
            return URLEncoder.encode(imgPath + "/" + uuid + "_" + imgName, "UTF-8");
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getThumbURL() {
        try {
            return URLEncoder.encode(imgPath + "/s_" + uuid + "_" + imgName, "UTF-8");
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
