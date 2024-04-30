package com.withus.withmebe.comment.dto.response;

import com.withus.withmebe.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record CommentResponse(
    Long id,
    String nickName,
    String commentContent,
    LocalDateTime createdDttm,
    LocalDateTime updatedDttm,
    LocalDateTime deletedDttm
) {


  public static CommentResponse fromEntity(Comment comment, String nickName) {
    return CommentResponse.builder()
        .id(comment.getId())
        .nickName(nickName)
        .commentContent(comment.getCommentContent())
        .createdDttm(comment.getCreatedDttm())
        .updatedDttm(comment.getUpdatedDttm())
        .deletedDttm(comment.getDeletedDttm())
        .build();
  }
}
