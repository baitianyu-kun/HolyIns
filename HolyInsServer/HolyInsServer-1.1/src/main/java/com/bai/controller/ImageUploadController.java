package com.bai.controller;

import com.bai.utils.ImageUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class ImageUploadController {
    @RequestMapping("/downloadImage")
    public String downloadImage(HttpServletResponse response) {
        String filename="test_image_dowload.png";
        try {
            response.setHeader("Content-Disposition","attachment;filename="+filename);
            OutputStream outputStream=response.getOutputStream();
            byte[] imagesbyte= ImageUtils.readImageToByteArray("D:\\test.png");
            outputStream.write(imagesbyte);
            outputStream.flush();
            outputStream.close();
            return "downloadSuccess";
        } catch (IOException e) {
            e.printStackTrace();
            return "downloadFailed";
        }
    }
    @RequestMapping("/uploadpdf")
    public String uploadPdf(@RequestParam("pdf")MultipartFile multipartFile)
    {
        String localPath = "/";
        String filename = null;
        try {
            if (!multipartFile.isEmpty()) {
                //文件名
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                //文件类型
                String contentType = multipartFile.getContentType();
                //文件后缀名
                String suffixName = contentType.substring(contentType.indexOf("/") + 1);
                //新的文件名
                filename = uuid + "." + suffixName;
                multipartFile.transferTo(new File(localPath + filename));
                System.err.println("multipartFile.getSize()=" + multipartFile.getSize());
            }
            return "upload success！";
        } catch (IOException e) {
            return "upload failed";
        }
    }


    @RequestMapping("/image")
    public String uploadHeadPic(@RequestParam("image") MultipartFile multipartFile) {
        String localPath = "D:\\";
        String filename = null;
        try {
            if (!multipartFile.isEmpty()) {
                //文件名
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                //文件类型
                String contentType = multipartFile.getContentType();
                //文件后缀名
                String suffixName = contentType.substring(contentType.indexOf("/") + 1);
                //String originName=multipartFile.getName();
                //String newfile=uuid+'.'+originName.substring(originName.indexOf('.')-1);
                //System.err.println("new_file="+newfile);
                //System.err.println("multipartFile.getName="+multipartFile.getOriginalFilename());
                String originName=multipartFile.getOriginalFilename();
                String newFileName=uuid+'.'+originName.substring(originName.indexOf('.'));
                //新的文件名
                //filename = uuid + "." + suffixName;
                multipartFile.transferTo(new File(localPath + newFileName));
                System.err.println("multipartFile.getSize()=" + multipartFile.getSize());
            }
            return "插入成功！";
        } catch (IOException e) {
            return "插入失败";
        }
    }
}
