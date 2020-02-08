package com.example.infrastructure.utils;

import com.example.domain.article.valueobject.Picture;
import com.example.domain.execption.BusinessException;
import org.apache.commons.lang3.ArrayUtils;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.example.domain.common.Constant.PICTURE_FORMATS;
import static com.example.domain.common.Constant.VIDEO_FORMATS;

@Component
public class UploadUtils {

    private static String pictureFilePath;
    private static String videoFilePath;

    public static String uploadVideo(MultipartFile file) {
        String fileExt = file.getOriginalFilename()
                .substring(file.getOriginalFilename().lastIndexOf(".") + 1)
                .toLowerCase();
        if (!VIDEO_FORMATS.contains(fileExt)) {
            throw new BusinessException("占只支持MP4格式视频上传");
        }
        // 重构文件名称
        String pikId = UUID.randomUUID().toString().replaceAll("-", "");
        String newVideoName = pikId + "." + fileExt;
        File fileSave = new File(videoFilePath, newVideoName);
        try {
            file.transferTo(fileSave);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(String.format("上传视频失败：%s", e.getMessage()));
        }

        return newVideoName;
    }

    public static String snapshotVideo(String fileName) {
        File file = new File(videoFilePath, fileName);
        if (!file.exists()) {
            throw new BusinessException(String.format("视频文件不存在（%s）", fileName));
        }

        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file);
        try {
            ff.start();

            // 截取第一帧图片
            int i = 0;
            int length = ff.getLengthInFrames();

            Frame frame = null;
            while (i < length) {
                frame = ff.grabFrame();
                if ((i >= 0) && (frame.image != null)) {
                    break;
                }
                i++;
            }

            // 截取的帧图片
            Java2DFrameConverter converter = new Java2DFrameConverter();
            BufferedImage srcImage = converter.getBufferedImage(frame);

            String pikId = UUID.randomUUID().toString().replaceAll("-", "");
            String pictureName = pikId + "." + "jpg";

            File picFile = new File(pictureFilePath, pictureName);
            ImageIO.write(srcImage, "jpg", picFile);

            ff.stop();

            return pictureName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(String.format("截取视频帧失败：%s", e.getMessage()));
        }
    }

    public static List<Picture> uploadPicture(MultipartFile[] file) {
        List<Picture> pictures = new ArrayList<>();
        if (!ArrayUtils.isEmpty(file)) {
            Arrays.stream(file).forEach(multipartFile -> {
                String fileExt = multipartFile.getOriginalFilename()
                        .substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1)
                        .toLowerCase();
                if (!PICTURE_FORMATS.contains(fileExt)) {
                    throw new BusinessException(String.format("占不只支持%s格式图片上传", fileExt));
                }
                // 重构文件名称
                String pikId = UUID.randomUUID().toString().replaceAll("-", "");
                String newPictureName = pikId + "." + fileExt;
                File fileSave = new File(pictureFilePath, newPictureName);
                try {
                    multipartFile.transferTo(fileSave);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new BusinessException(String.format("上传图片失败：%s", e.getMessage()));
                }

                pictures.add(Picture.builder().path(newPictureName).build());
            });
        }

        return pictures;
    }

    @Value("${upload-file-path.video}")
    public void setVideoFilePath(String videoFilePath) {
        UploadUtils.videoFilePath = videoFilePath;
    }

    @Value("${upload-file-path.picture}")
    public void setPictureFilePath(String pictureFilePath) {
        UploadUtils.pictureFilePath = pictureFilePath;
    }
}
