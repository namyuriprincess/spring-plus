package org.example.expert.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.dto.request.CommentSaveRequest;
import org.example.expert.domain.comment.dto.response.CommentResponse;
import org.example.expert.domain.comment.dto.response.CommentSaveResponse;
import org.example.expert.domain.comment.entity.Comment;
import org.example.expert.domain.comment.repository.CommentRepository;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.repository.TodoRepository;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final TodoRepository todoRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public List<CommentResponse> saveComment(AuthUser authUser, long todoId, CommentSaveRequest commentSaveRequest) {
        User user = User.fromAuthUser(authUser);
        Todo todo = todoRepository.findById(todoId).orElseThrow(() ->
                new InvalidRequestException("Todo not found"));

        Comment newComment = new Comment(
                commentSaveRequest.getContents(),
                user,
                todo
        );

        List<Comment> comments = commentRepository.findAllByTodoIdWithUser(todoId);
        Comment savedComment = commentRepository.save(newComment);

        return comments.stream()
                .map(comment -> new CommentResponse(
                        comment.getId(),
                        comment.getContents(),
                        comment.getUser().getNickname(),
                        comment.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    public List<CommentResponse> getComments(long todoId) {
        List<Comment> commentList = commentRepository.findByTodoIdWithUser(todoId);

        return commentList.stream()
                .map(comment -> new CommentResponse(
                        comment.getId(),
                        comment.getContents(),
                        comment.getUser().getNickname(),
                        comment.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }
}
