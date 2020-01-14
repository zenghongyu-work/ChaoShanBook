package com.example.controller.video;

import com.example.app.VideoApp;
import com.example.controller.common.Operator;
import com.example.controller.common.Result;
import com.example.controller.upload.UploadResponse;
import com.example.controller.video.VideoRequest.*;
import com.example.domain.execption.BusinessException;
import com.example.domain.video.Video;
import com.example.infrastructure.utils.DataUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static com.example.domain.common.Constant.VIDEO_FORMATS;

@Api(tags = {"短视频接口"})
@RestController
@RequestMapping("/video")
public class VideoController {

    @Value("${upload-file-path.video}")
    private String videoFilePath;

    @Autowired
    private VideoApp videoApp;

    @Autowired
    private Operator operator;

    @ApiOperation(value = "创建")
    @PostMapping
    public Result add(CreateVideo request) {
        Video video = Video.builder().build();
        BeanUtils.copyProperties(request, video);
        video.setCreateBy(operator.getId());
        video.setCreateAt(DataUtils.getCurrentDataTime());
        video.setVideo(uploadVideo(request.getVideo()));
        videoApp.add(video);
        return Result.builder()
                .data(video)
                .build();
    }

    @ApiOperation(value = "根据Id获取")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "短视频Id", required = true, dataType = "Integer", paramType = "path")})
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        return Result.builder()
                .data(videoApp.getById(id))
                .build();
    }

    @ApiOperation(value = "获取列表")
    @GetMapping
    public Result list() {
        return Result.builder()
                .data(videoApp.list())
                .build();
    }

    public String uploadVideo(MultipartFile file) {
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
}
