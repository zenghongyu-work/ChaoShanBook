package com.example.app.comment;

import com.example.domain.comment.Comment;
import com.example.domain.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CommentApp {

    @Autowired
    private CommentService commentService;

    @Transactional(rollbackFor = Exception.class)
    public Comment add(Comment comment) {
        return commentService.add(comment);
    }

    public List<Comment> listTopByRelationIdAndType(Integer relationId, String type) {
        return commentService.listTopByRelationIdAndType(relationId, type);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Integer id) {
        commentService.deleteById(id);
    }

    public Comment getById(Integer id) {
        return commentService.getById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Comment praise(Comment comment) {
        return commentService.praise(comment);
    }

    @Transactional(rollbackFor = Exception.class)
    public Comment cancelPraise(Comment comment) {
        return commentService.cancelPraise(comment);
    }

}
