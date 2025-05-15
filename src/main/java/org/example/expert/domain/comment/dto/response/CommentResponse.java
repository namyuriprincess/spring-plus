package org.example.expert.domain.comment.dto.response;

import lombok.Getter;
import org.example.expert.domain.user.dto.response.UserResponse;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {

    private final Long id;
    private final String contents;
    private String nickname;
    private LocalDateTime createdAt;

    public CommentResponse(Long id, String contents, String nickname, LocalDateTime createdAt) {
        this.id = id;
        this.contents = contents;
        this.nickname = nickname;
        this.createdAt = createdAt;
    }

}
