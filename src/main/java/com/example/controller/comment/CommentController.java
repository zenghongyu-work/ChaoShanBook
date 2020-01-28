package com.example.controller.comment;

import com.example.app.comment.CommentApp;
import com.example.controller.common.Operator;
import com.example.controller.common.Result;
import com.example.domain.comment.Comment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


@Api(tags = {"评论接口"})
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Value("${picture-base-url}")
    private String pictureBaseUrl;

    @Autowired
    private CommentApp commentApp;

    @Autowired
    private Operator operator;

    @ApiOperation(value = "新增评论")
    @PostMapping
    public Result add(@RequestBody CommentRequest.Create request) {
        Comment comment = Comment.builder().build();
        BeanUtils.copyProperties(request, comment);
        comment.setUserId(operator.getId());
        commentApp.add(comment);
        return Result.builder()
                .msg("新增成功")
                .data(handleIcon(comment))
                .build();
    }

    private Comment handleIcon(Comment comment) {
        comment.setUserIcon(pictureBaseUrl + comment.getUserIcon());
        comment.setToUserIcon(pictureBaseUrl + comment.getToUserIcon());
        return comment;
    }

    @ApiOperation(value = "删除评论")
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id) {
        commentApp.deleteById(id);
        return Result.builder()
                .msg("删除成功")
                .build();
    }

    @ApiOperation(value = "获取资源下的评论")
    @GetMapping("/relationId/{relationId}/type/{type}")
    public Result listTopByRelationIdAndType(@PathVariable Integer relationId, @PathVariable String type) {
        return Result.builder()
                .data(commentApp.listTopByRelationIdAndType(relationId, type).stream()
                        .map(comment -> handleIcon(comment)))
                .build();
    }

    @ApiOperation(value = "点赞评论")
    @PostMapping("/praise")
    public Result praise(@RequestBody CommentRequest.Praise request) {
        Comment comment = commentApp.getById(request.getId());

        if ("0".equals(request.getType())) { // 取消点赞
            return Result.builder()
                    .msg("取消点赞成功")
                    .data(handleIcon(commentApp.cancelPraise(comment)))
                    .build();
        } else if ("1".equals(request.getType())) { // 点赞
            return Result.builder()
                    .msg("点赞成功")
                    .data(handleIcon(commentApp.praise(comment)))
                    .build();
        }

        return null;
    }

}
