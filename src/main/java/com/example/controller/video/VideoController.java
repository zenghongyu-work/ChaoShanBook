package com.example.controller.video;

import com.example.app.VideoApp;
import com.example.controller.common.Operator;
import com.example.controller.common.Result;
import com.example.controller.video.VideoRequest.*;
import com.example.domain.video.Video;
import com.example.infrastructure.utils.DataUtils;
import com.example.infrastructure.utils.UploadUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"短视频接口"})
@RestController
@RequestMapping("/video")
public class VideoController {

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
        video.setVideo(UploadUtils.uploadVideo((request.getVideo())));
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

}
