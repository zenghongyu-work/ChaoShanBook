package com.example.domain.comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment add(Comment comment);

    Comment update(Comment comment);

    void deleteById(Integer id);

    List<Comment> listTopByRelationIdAndType(Integer relationId, String type);

    List<Comment> listByCommentId(Integer commentId);

    Optional<Comment> getById(Integer id);
}
