package org.example.stamppaw_backend.user.service;

import jakarta.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.example.stamppaw_backend.common.exception.ErrorCode;
import org.example.stamppaw_backend.common.exception.StampPawException;
import org.example.stamppaw_backend.user.dto.response.UserResponseDto;
import org.example.stamppaw_backend.user.entity.User;
import org.example.stamppaw_backend.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserOrException(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new StampPawException(ErrorCode.USER_NOT_FOUND));
    }

    public UserResponseDto getMyInfo(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
            .orElseThrow(() -> new StampPawException(ErrorCode.USER_NOT_FOUND));

        return new UserResponseDto(
            user.getId(),
            user.getNickname(),
            user.getEmail(),
            user.getRegion(),
            user.getBio(),
            user.getProfileImage()
        );
    }

    @Transactional
    public UserResponseDto updateMyInfo(
        UserDetails userDetails,
        String nickname,
        MultipartFile profileImage
    ) {

        User user = userRepository.findByEmail(userDetails.getUsername())
            .orElseThrow(() -> new StampPawException(ErrorCode.USER_NOT_FOUND));

        if (nickname != null) {
            user.setNickname(nickname);
        }

        // 반드시 마지막에 "/" 포함
        String uploadDir = "/Users/pooroome/Downloads/profile/";

        // 폴더 없으면 생성
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        if (profileImage != null && !profileImage.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + profileImage.getOriginalFilename();

            File saveFile = new File(uploadDir + fileName);
            try {
                profileImage.transferTo(saveFile);
                user.setProfileImage(fileName);
            } catch (IOException e) {
                throw new StampPawException(ErrorCode.FILE_UPLOAD_FAILED);
            }
        }

        userRepository.save(user);

        return new UserResponseDto(
            user.getId(),
            user.getNickname(),
            user.getEmail(),
            user.getRegion(),
            user.getBio(),
            user.getProfileImage()
        );
    }
}
