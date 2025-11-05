package org.example.stamppaw_backend.user.service;

import lombok.RequiredArgsConstructor;
import org.example.stamppaw_backend.common.exception.ErrorCode;
import org.example.stamppaw_backend.common.exception.StampPawException;
import org.example.stamppaw_backend.user.entity.User;
import org.example.stamppaw_backend.user.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserOrExcepion(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new StampPawException(ErrorCode.USER_NOT_FOUND));

    }
}
