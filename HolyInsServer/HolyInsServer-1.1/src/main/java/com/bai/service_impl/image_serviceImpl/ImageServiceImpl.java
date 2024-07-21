package com.bai.service_impl.image_serviceImpl;

import com.alibaba.fastjson.JSON;
import com.bai.pojo.UploadImageInfo;
import com.bai.service.image_service.ImageService;
import com.bai.state.ActivityStatus;
import com.bai.state.StorageState;
import com.bai.utils.ImageUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {


    @Override
    /*
     * @Description:
     * @Param: [multipartFile 要上传的图片文件, imagePath 图片文件的地址]
     * @Return: java.lang.String 上传成功返回文件的大小，失败返回上传失败
     * @Author: baitianyu
     * @Date: 2021/4/1 16:41
     **/
    public String uploadImage(MultipartFile multipartFile, String storagePlace) {
        ClassPathResource classPathResource = new ClassPathResource("");
        try {
            //这个是整个项目的resources目录的地址，例如D:/HolyInsServer-1.1/out/artifacts/HolyInsServer_1_1_war_exploded/WEB-INF/classes
            String resourcePath = classPathResource.getFile().getPath();
            //文件类型
            if (!multipartFile.isEmpty()) {
                //文件名,使用随机uuid
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                //原文件名
                String origin_filename = multipartFile.getOriginalFilename();
                //新的文件名(带扩展名的那种)
                String new_filename = uuid + '.' + origin_filename.substring(origin_filename.indexOf('.') + 1);
                //把所有正着的斜杠都返回来
                //String data_base_imagePathAndFileName = (resourcePath + storagePlace + new_filename).replaceAll("\\\\", "/");
                try {
                    //创建新文件用的是全部的路径
                    multipartFile.transferTo(new File(resourcePath + storagePlace + new_filename));
                    //插入到数据库中的应该是相对路径，也就是/pictures/photo/,即storagePlace
                    //上传成功后返回这个文件的信息
                    return JSON.toJSONString(new UploadImageInfo(storagePlace + new_filename, multipartFile.getSize()));
                } catch (IOException e) {
                    return ActivityStatus.STRING_IMAGE_UPLOAD_FAILED;
                }
            } else {
                return ActivityStatus.STRING_IMAGE_IS_EMPTY;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ActivityStatus.STRING_UPLOAD_FAILED;
        }
    }

    @Override
    public String uploadPhoto(MultipartFile multipartFile) {
        return uploadImage(multipartFile, StorageState.DEFAULT_PHOTO_LOCATE);
    }

    @Override
    public String uploadHeadPic(MultipartFile multipartFile) {
        return uploadImage(multipartFile, StorageState.DEFAULT_HEAD_PIC_LOCATE);
    }


    @Override
    /*
     * @Description:
     * @Param: [response, imagePath]  response imagepath图片路径
     * @Return: java.lang.String
     * @Author: baitianyu
     * @Date: 2021/4/3 11:22
     **/

    public String getImage(HttpServletResponse response, String imagePath) {
        // ClassPathResource类的构造方法接收路径名称，自动去classpath路径下找文件
        ClassPathResource classPathResource = new ClassPathResource(imagePath);
        // 获得File对象，当然也可以获取输入流对象
        File file = null;
        try {
            file = classPathResource.getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //File file = new File(imagePath);
        try {
            //通过response来传送图片,得获取图片名字
            response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
            OutputStream outputStream = response.getOutputStream();
            byte[] imagesbyte = ImageUtils.readImageToByteArray(file);
            outputStream.write(imagesbyte);
            outputStream.flush();
            outputStream.close();
            return ActivityStatus.STRING_GET_IMAGE_SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
            return ActivityStatus.STRING_GET_IMAGE_FAILED;
        }
    }

    @Override
    public String getHeadPic(HttpServletResponse response, String headPicPath) {
        return getImage(response, headPicPath);
    }

    @Override
    public String getPhoto(HttpServletResponse response, String photoPath) {
        return getImage(response, photoPath);
    }


}
