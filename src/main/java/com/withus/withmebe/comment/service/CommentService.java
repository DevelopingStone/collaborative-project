package com.withus.withmebe.comment.service;

import static com.withus.withmebe.common.exception.ExceptionCode.AUTHORIZATION_ISSUE;
import static com.withus.withmebe.common.exception.ExceptionCode.ENTITY_NOT_FOUND;

import com.withus.withmebe.comment.dto.request.SetCommentRequest;
import com.withus.withmebe.comment.dto.response.CommentResponse;
import com.withus.withmebe.comment.entity.Comment;
import com.withus.withmebe.comment.repository.CommentRepository;
import com.withus.withmebe.comment.dto.request.AddCommentRequest;
import com.withus.withmebe.common.exception.CustomException;
import com.withus.withmebe.gathering.repository.GatheringRepository;
import com.withus.withmebe.member.entity.Member;
import com.withus.withmebe.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final MemberRepository memberRepository;
  private final GatheringRepository gatheringRepository;

  @Transactional
  public CommentResponse createComment(long requesterId, long gatheringId, AddCommentRequest request) {

    Member requester = readRequester(requesterId);
    checkGatheringExist(gatheringId);

    Comment newComment = commentRepository.save(request.toEntity(gatheringId, requester));
    return newComment.toResponse();
  }

  @Transactional(readOnly = true)
  public Page<CommentResponse> readComments(long gatheringId, Pageable pageable) {

    Pageable adjustedPageable = adjustPageable(pageable);
    Page<Comment> comments = commentRepository.findCommentsByGatheringId(gatheringId, adjustedPageable);
    return comments.map(Comment::toResponse);
  }

  private Pageable adjustPageable(Pageable pageable) {

    int size = Math.max(pageable.getPageSize(), 1);
    int page = Math.max(pageable.getPageNumber(), 0);
    return PageRequest.of(page, size);
  }

  @Transactional
  public CommentResponse updateComment(long requesterId, long commentId, SetCommentRequest request) {

    Comment comment = readEditableComment(requesterId, commentId);

    comment.setCommentContent(request.commentContent());
    Comment updatedComment = readComment(commentId);
    return updatedComment.toResponse();
  }

  public CommentResponse deleteComment(long requesterId, long commentId) {

    Comment comment = readEditableComment(requesterId, commentId);

    commentRepository.delete(comment);
    return comment.toResponse();
  }

  private Comment readEditableComment(long requesterId, long commentId) {

    Comment comment = readComment(commentId);
    if (!comment.isWriter(requesterId)) {
      throw new CustomException(AUTHORIZATION_ISSUE);
    }
    return comment;
  }

  private Comment readComment(long commentId) {
    return commentRepository.findById(commentId)
        .orElseThrow(() -> new CustomException(ENTITY_NOT_FOUND));
  }

  private Member readRequester(long requesterId) {
    return memberRepository.findById(requesterId)
        .orElseThrow(() -> new CustomException(ENTITY_NOT_FOUND));
  }

  private void checkGatheringExist(long gatheringId) {
    if(!gatheringRepository.existsById(gatheringId)) {
      throw new CustomException(ENTITY_NOT_FOUND);
    }
  }
}
