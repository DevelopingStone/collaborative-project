package com.withus.withmebe.gathering.service;

import static com.withus.withmebe.common.exception.ExceptionCode.ENTITY_NOT_FOUND;

import com.withus.withmebe.common.exception.CustomException;
import com.withus.withmebe.common.exception.ExceptionCode;
import com.withus.withmebe.gathering.dto.request.AddGatheringRequest;
import com.withus.withmebe.gathering.dto.request.SetGatheringRequest;
import com.withus.withmebe.gathering.dto.response.AddGatheringResponse;
import com.withus.withmebe.gathering.dto.response.DeleteGatheringResponse;
import com.withus.withmebe.gathering.dto.response.GetGatheringResponse;
import com.withus.withmebe.gathering.dto.response.SetGatheringResponse;
import com.withus.withmebe.gathering.entity.Gathering;
import com.withus.withmebe.gathering.repository.GatheringRepository;
import com.withus.withmebe.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GatheringService {

    private final GatheringRepository gatheringRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public AddGatheringResponse createGathering(long currentMemberId, AddGatheringRequest addGatheringRequest) {
        findByMemberId(currentMemberId);
        Gathering gathering = gatheringRepository.save(addGatheringRequest.toEntity(currentMemberId));
        return AddGatheringResponse.toResponse(gathering);
    }

    public Page<GetGatheringResponse> readGatheringList(Pageable pageable) {
        Pageable adjustedPageable = adjustPageable(pageable);
        Page<Gathering> gatherings = gatheringRepository.findAll(adjustedPageable);
        return gatherings.map(GetGatheringResponse::toResponse);
    }

    @Transactional
    public SetGatheringResponse updateGathering(long currentMemberId, long gatheringId, SetGatheringRequest setGatheringRequest) {
        Gathering gathering = getGathering(currentMemberId, gatheringId);
        gathering.updateGatheringFields(setGatheringRequest);
        return SetGatheringResponse.toResponse(gathering);
    }

    public GetGatheringResponse readGathering(Long gatheringId) {
        return GetGatheringResponse.toResponse(findByGatheringId(gatheringId));
    }

    public DeleteGatheringResponse deleteGathering(long currentMemberId, long gatheringId) {
        Gathering gathering = getGathering(currentMemberId, gatheringId);
        gatheringRepository.deleteById(gatheringId);
        return DeleteGatheringResponse.toResponse(gathering);
    }

    private Pageable adjustPageable(Pageable pageable) {
        int size = Math.max(pageable.getPageSize(), 1);
        int page = Math.max(pageable.getPageNumber(), 0);
        return PageRequest.of(page, size);
    }

    private Gathering getGathering(long memberId, long gatheringId) {
        Gathering gathering = findByGatheringId(gatheringId);
        if (memberId != gathering.getMemberId()) {
            throw new CustomException(ExceptionCode.AUTHORIZATION_ISSUE);
        }
        return gathering;
    }

    private Gathering findByGatheringId(long gatheringId) {
        return gatheringRepository.findById(gatheringId).orElseThrow(() -> new CustomException(ENTITY_NOT_FOUND));
    }

    private void findByMemberId(long memberId) {
        memberRepository.findById(memberId).orElseThrow(() -> new CustomException(ENTITY_NOT_FOUND));
    }
}
