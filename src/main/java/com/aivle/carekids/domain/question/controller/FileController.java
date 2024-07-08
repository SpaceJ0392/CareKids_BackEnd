package com.aivle.carekids.domain.question.controller;

import com.aivle.carekids.domain.question.service.FileService;
import com.aivle.carekids.domain.user.general.jwt.constants.JwtUtils;
import com.aivle.carekids.domain.user.models.Users;
import com.aivle.carekids.domain.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FileController {

    private final FileService fileService;
    private final UsersRepository usersRepository;

    private final ModelMapper dtoModelMapper;
    private final JwtUtils jwtUtils;

    @PostMapping("/file")
    public ResponseEntity<?> uploadFiles(@CookieValue(name = "AccessToken") String accessToken,
                                         @CookieValue(name = "RefreshToken") String refreshToken,
                                         @RequestBody MultipartFile multipartFile){

        Map<String, String> tokens = jwtUtils.verifyJWTs(accessToken, refreshToken);
        if (tokens.get("status") != null){
            return ResponseEntity.badRequest().body(Map.of("message", "로그인이 필요합니다."));
        }

        HttpHeaders headers = new HttpHeaders();
        if (tokens.get("access_token") != null) {
            headers.add(HttpHeaders.SET_COOKIE, tokens.get("access_token"));
        }

        Long usersId = JwtUtils.getUsersId(JwtUtils.verifyToken(accessToken));
        Optional<Users> targetUsers = usersRepository.findByUsersId(usersId);

        if (targetUsers.isEmpty()) {
            return ResponseEntity.badRequest().headers(headers).body(Map.of("message", "존재하지 않는 사용자입니다."));
        }

        return ResponseEntity.status(HttpStatus.OK).headers(headers)
                .body(fileService.uploadFile(targetUsers.get().getUsersNickname(), multipartFile));
    }
}
