package org.example.stamppaw_backend.companion.service;

import lombok.RequiredArgsConstructor;
import org.example.stamppaw_backend.common.exception.ErrorCode;
import org.example.stamppaw_backend.common.exception.StampPawException;
import org.example.stamppaw_backend.companion.entity.Companion;
import org.example.stamppaw_backend.companion.entity.CompanionApply;
import org.example.stamppaw_backend.companion.repository.CompanionApplyRepository;
import org.example.stamppaw_backend.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanionApplyService {
    private final CompanionApplyRepository companionApplyRepository;

    public void saveCompanionApply(User user, Companion companion) {
        isAlreadyApply(user, companion.getApplies());
        CompanionApply companionApply = CompanionApply.builder()
                .companion(companion)
                .applicant(user)
                .build();

        companionApplyRepository.save(companionApply);
    }

    private void isAlreadyApply(User user, List<CompanionApply> applies) {
        for(CompanionApply apply : applies) {
            if(apply.getApplicant().equals(user)) {
                throw new StampPawException(ErrorCode.ALREADY_APPLICANT);
            }
        }
    }
}
