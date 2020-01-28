package com.example.domain.comment;

import com.example.controller.common.Operator;
import com.example.domain.execption.BusinessException;
import com.example.domain.execption.NotFoundException;
import com.example.domain.user.User;
import com.example.domain.user.UserService;
import com.example.infrastructure.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private Operator operator;

    public Comment add(Comment comment) {
        User user = userService.getById(comment.getUserId());
        comment.setUserNickname(user.getNickname());
        comment.setUserIcon(user.getIcon());

        if (comment.getToUserId() != null && comment.getToUserId().intValue() != 0) {
            User toUser = userService.getById(comment.getToUserId());
            comment.setToUserNickname(toUser.getNickname());
            comment.setToUserIcon(toUser.getIcon());
        }

        comment.setCreateAt(DataUtils.getCurrentDataTime());
        commentRepository.add(comment);
        return comment;
    }

    public void deleteById(Integer id) {
        Optional<Comment> optional = commentRepository.getById(id);
        if (optional.isPresent()) {
            Comment comment = optional.get();
            if (!comment.getUserId().equals(operator.getId())) {
                throw new BusinessException("无权删除该评论");
            }

            commentRepository.deleteById(id);
        }
    }

    public List<Comment> listTopByRelationIdAndType(Integer relationId, String type) {
        List<Comment> comments = commentRepository.listTopByRelationIdAndType(relationId, type);

        return comments.stream().map(comment -> {
            comment.setSubComments(commentRepository.listByCommentId(comment.getId()));
            return comment;
        }).collect(Collectors.toList());
    }

    public Comment praise(Comment comment) {
        comment.setPraiseCount(comment.getPraiseCount() + 1);
        return commentRepository.update(comment);
    }

    public Comment cancelPraise(Comment comment) {
        comment.setPraiseCount(comment.getPraiseCount() - 1 < 0 ? 0 : comment.getPraiseCount() - 1);
        return commentRepository.update(comment);
    }

    public Comment getById(Integer id) {
        Comment comment = commentRepository.getById(id).orElseThrow(() -> new NotFoundException(String.format("评论不存在（%d）", id)));
        comment.setSubComments(commentRepository.listByCommentId(comment.getId()));
        return comment;
    }
}
