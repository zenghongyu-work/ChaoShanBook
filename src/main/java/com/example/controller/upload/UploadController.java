package com.example.controller.upload;


import com.example.controller.common.Result;
import com.example.domain.execption.BusinessException;
import com.example.domain.execption.NotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static com.example.domain.common.Constant.PICTURE_FORMATS;
import static com.example.domain.common.Constant.VIDEO_FORMATS;

@Api(tags = {"文件接口"})
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Value("${upload-file-path.video}")
    private String videoFilePath;

    @Value("${upload-file-path.picture}")
    private String pictureFilePath;

    @Autowired
    private NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;

//    @ApiOperation(value = "视频上传")
//    @ApiImplicitParams({@ApiImplicitParam(name = "file", value = "视频文件", required = true, dataType = "file", paramType = "form")})
//    @PostMapping("/video")
//    public Result uploadVideo(@RequestParam("file") MultipartFile file) {
//        String fileExt = file.getOriginalFilename()
//                .substring(file.getOriginalFilename().lastIndexOf(".") + 1)
//                .toLowerCase();
//        if (!VIDEO_FORMATS.contains(fileExt)) {
//            throw new BusinessException("占只支持MP4格式视频上传");
//        }
//        // 重构文件名称
//        String pikId = UUID.randomUUID().toString().replaceAll("-", "");
//        String newVideoName = pikId + "." + fileExt;
//        File fileSave = new File(videoFilePath, newVideoName);
//        try {
//            file.transferTo(fileSave);
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new BusinessException(String.format("上传视频失败：%s", e.getMessage()));
//        }
//
//        return Result.builder()
//                .data(UploadResponse.Video.builder().path(newVideoName).build())
//                .build();
//    }

    @ApiOperation(value = "视频获取")
    @ApiImplicitParams({@ApiImplicitParam(name = "path", value = "视频路径", required = true, dataType = "String", paramType = "path")})
    @GetMapping("/video/{path}")
    public void getVideo(@PathVariable("path") String path, HttpServletRequest request, HttpServletResponse response) {
        String fileExt = path.substring(path.lastIndexOf(".") + 1)
                .toLowerCase();
        if (!VIDEO_FORMATS.contains(fileExt)) {
            throw new BusinessException("占只支持MP4格式视频查看");
        }

        Path filePath = Paths.get(videoFilePath, path);
        if (!Files.exists(filePath)) {
            throw new NotFoundException(String.format("视频不存在（%s）", path));
        }

        String mimeType = probeContentType(filePath);
        if (!StringUtils.isEmpty(mimeType)) {
            response.setContentType(mimeType);
        }
        request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, filePath);
        try {
            nonStaticResourceHttpRequestHandler.handleRequest(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
            throw new BusinessException(String.format("获取视频失败：%s", e.getMessage()));
        }
    }

//    @ApiOperation(value = "图片上传")
//    @ApiImplicitParams({@ApiImplicitParam(name = "file", value = "图片文件", required = true, dataType = "file", paramType = "form")})
//    @PostMapping("/picture")
//    public Result uploadPicture(@RequestParam("file") MultipartFile[] file) {
//        List<String> paths = new ArrayList<>();
//        if (!ArrayUtils.isEmpty(file)) {
//            Arrays.stream(file).forEach(multipartFile -> {
//                String fileExt = multipartFile.getOriginalFilename()
//                        .substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1)
//                        .toLowerCase();
//                if (!PICTURE_FORMATS.contains(fileExt)) {
//                    throw new BusinessException(String.format("占不只支持%s格式图片上传", fileExt));
//                }
//                // 重构文件名称
//                String pikId = UUID.randomUUID().toString().replaceAll("-", "");
//                String newPictureName = pikId + "." + fileExt;
//                File fileSave = new File(pictureFilePath, newPictureName);
//                try {
//                    multipartFile.transferTo(fileSave);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    throw new BusinessException(String.format("上传图片失败：%s", e.getMessage()));
//                }
//
//                paths.add(newPictureName);
//            });
//        }
//
//        return Result.builder()
//                .data(UploadResponse.Picture.builder().paths(paths).build())
//                .build();
//    }

    @ApiOperation(value = "图片获取")
    @ApiImplicitParams({@ApiImplicitParam(name = "path", value = "图片路径", required = true, dataType = "String", paramType = "path")})
    @GetMapping("/picture/{path}")
    public void getPicture(@PathVariable("path") String path, HttpServletResponse response) {
        String fileExt = path.substring(path.lastIndexOf(".") + 1)
                .toLowerCase();
        if (!PICTURE_FORMATS.contains(fileExt)) {
            throw new BusinessException(String.format("占不只支持%s格式图片查看", fileExt));
        }

        Path filePath = Paths.get(pictureFilePath, path);
        if (!Files.exists(filePath)) {
            throw new NotFoundException(String.format("图片不存在（%s）",path));
        }

        String mimeType = probeContentType(filePath);
        if (!StringUtils.isEmpty(mimeType)) {
            response.setContentType(mimeType);
        }

        try (OutputStream outputStream = response.getOutputStream();
             InputStream inputStream = new FileInputStream(filePath.toFile())){
            byte[] bytes = new byte[8 * 1024 * 1024];
            int length;
            while ((length = IOUtils.read(inputStream, bytes)) > 0) {
                outputStream.write(bytes, 0, length);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(String.format("获取图片失败：%s", e.getMessage()));
        }
    }

    private static String probeContentType(Path path) {
        String mimeType;
        try {
            mimeType = Files.probeContentType(path);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(String.format("获取ContentType失败：%s", e.getMessage()));
        }

        return mimeType;
    }
}
