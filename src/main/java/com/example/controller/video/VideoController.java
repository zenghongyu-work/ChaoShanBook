package com.example.controller.video;

import com.example.app.VideoApp;
import com.example.controller.common.Operator;
import com.example.controller.video.VideoRequest.*;
import com.example.domain.video.Video;
import com.example.infrastructure.utils.DataUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Video add(@RequestBody Create request) {
        Video video = Video.builder().build();
        BeanUtils.copyProperties(request, video);
        video.setCreateBy(operator.getId());
        video.setCreateAt(DataUtils.getCurrentDataTime());
        videoApp.add(video);
        return video;
    }

    @ApiOperation(value = "根据Id获取")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "短视频Id", required = true, dataType = "Integer", paramType = "path")})
    @GetMapping("/{id}")
    public Video getById(@PathVariable Integer id) {
        return videoApp.getById(id);
    }

    @ApiOperation(value = "获取列表")
    @GetMapping
    public List<Video> list() {
        return videoApp.list();
    }
}
