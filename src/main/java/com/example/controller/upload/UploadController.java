package com.example.controller.upload;


import com.example.domain.execption.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static com.example.domain.common.Constant.VIDEO_FORMATS;

@Api(tags = {"文件接口"})
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Value("${upload-file-path.video}")
    private String videoFilePath;

    @Autowired
    private NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;

    @ApiOperation(value = "视频上传")
    @ApiImplicitParams({@ApiImplicitParam(name = "file", value = "视频文件", required = true, dataType = "file", paramType = "form")})
    @PostMapping("/video")
    public UploadResponse uploadVideo(@RequestParam("file") MultipartFile file) {
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

        return UploadResponse.builder()
                .path(newVideoName)
                .build();
    }

    @ApiOperation(value = "视频获取")
    @ApiImplicitParams({@ApiImplicitParam(name = "path", value = "视频路径", required = true, dataType = "String", paramType = "path")})
    @GetMapping("/video/{path}")
    public void getVideo(@PathVariable("path") String path, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fileExt = path.substring(path.lastIndexOf(".") + 1)
                .toLowerCase();
        if (!VIDEO_FORMATS.contains(fileExt)) {
            throw new BusinessException("占只支持MP4格式视频查看");
        }

        Path filePath = Paths.get(videoFilePath, path);
        if (Files.exists(filePath)) {
            String mimeType = Files.probeContentType(filePath);
            if (!StringUtils.isEmpty(mimeType)) {
                response.setContentType(mimeType);
            }
            request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, filePath);
            nonStaticResourceHttpRequestHandler.handleRequest(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        }
    }
}
