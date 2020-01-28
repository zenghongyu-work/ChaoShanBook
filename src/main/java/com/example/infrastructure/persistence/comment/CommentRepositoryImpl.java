package com.example.infrastructure.persistence.comment;

import com.example.domain.comment.Comment;
import com.example.domain.comment.CommentRepository;
import com.example.infrastructure.persistence.user.UserDbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Comment add(Comment comment) {
        CommentDbo dbo = CommentDbo.fromModule(comment, CommentDbo.class);
        commentMapper.insert(dbo);
        comment.setId(dbo.getId());
        return comment;
    }

    @Override
    public Comment update(Comment comment) {
        CommentDbo dbo = CommentDbo.fromModule(comment, CommentDbo.class);
        commentMapper.updateByPrimaryKey(dbo);
        return comment;
    }

    @Override
    public void deleteById(Integer id) {
        commentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Comment> listTopByRelationIdAndType(Integer relationId, String type) {
        Example example = new Example(CommentDbo.class);
        example.createCriteria()
                .andEqualTo("relationId", relationId)
                .andEqualTo("type", type)
                .andEqualTo("commentId", 0);
        example.setOrderByClause("create_at desc");
        List<CommentDbo> commentDbos = commentMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(commentDbos)) {
            return Collections.emptyList();
        } else {
            return commentDbos.stream()
                    .map(commentDbo -> commentDbo.toModule(Comment.class))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<Comment> listByCommentId(Integer commentId) {
        Example example = new Example(CommentDbo.class);
        example.createCriteria().andEqualTo("commentId", commentId);
        example.setOrderByClause("create_at desc");
        List<CommentDbo> commentDbos = commentMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(commentDbos)) {
            return Collections.emptyList();
        } else {
            return commentDbos.stream()
                    .map(commentDbo -> commentDbo.toModule(Comment.class))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Optional<Comment> getById(Integer id) {
        Example example = new Example(CommentDbo.class);
        example.createCriteria().andEqualTo("id", id);
        CommentDbo commentDbo = commentMapper.selectOneByExample(example);
        if (commentDbo == null) {
            return Optional.empty();
        } else {
            return Optional.of(commentDbo.toModule(Comment.class));
        }
    }

}
