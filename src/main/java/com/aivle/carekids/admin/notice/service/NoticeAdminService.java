package com.aivle.carekids.admin.notice.service;

import com.aivle.carekids.domain.notice.dto.NoticeDto;
import com.aivle.carekids.domain.notice.models.Notice;
import com.aivle.carekids.domain.notice.repository.NoticeRepository;
import com.aivle.carekids.domain.question.service.FileService;
import com.aivle.carekids.domain.user.models.Role;
import com.aivle.carekids.domain.user.models.Users;
import com.aivle.carekids.domain.user.repository.UsersRepository;
import com.aivle.carekids.global.Variable.GlobelVar;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeAdminService {

    private final NoticeRepository noticeRepository;
    private final UsersRepository usersRepository;
    private final FileService fileService;

    @Transactional
    public ResponseEntity<?> editNotice(Long usersId, NoticeDto noticeDto, MultipartFile imgFile) throws IOException {

        Optional<Users> users = usersRepository.findByUsersId(usersId);
        if(users.isEmpty()) { return ResponseEntity.badRequest().body(Map.of("message", "사용자가 존재하지 않습니다")); }

        if (noticeDto.getNoticeId() == null){
            // 생성

            // 이미지 파일 저장 및 저장 url 반환
            noticeDto.setNoticeImgUrl(fileService.uploadFileNotice(imgFile));
            Notice newNotice = Notice.createNewNotice(noticeDto);
            newNotice.setUsersInfo(users.get());
            noticeRepository.save(newNotice);

            return  ResponseEntity.created(URI.create(GlobelVar.CLIENT_BASE_URL + "/api/admin/notice"))
                    .body(Map.of("message", "공지사항이 등록되었습니다."));
        }

        // 수정
        Optional<Notice> notice = noticeRepository.findById(noticeDto.getNoticeId());
        if(notice.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "존재하지 않는 공지사항입니다."));
        }

        noticeDto.setNoticeImgUrl(fileService.uploadFileNotice(imgFile));
        Notice targetNotice = notice.get();
        targetNotice.updateNotice(noticeDto);

        return ResponseEntity.ok(Map.of("message", "공지사항이 수정되었습니다."));
    }

    @Transactional
    public Map<String, ?> deleteNotice(Long usersId, Long noticeId) {
        Optional<Notice> targetNotice = noticeRepository.findById(noticeId);
        Optional<Users> users = usersRepository.findByUsersId(usersId);

        if (targetNotice.isEmpty() || users.isEmpty()) { return null; }
        if (users.get().getUsersRole() != Role.ROLE_ADMIN){ return null; }

        targetNotice.get().setDeletedInfo(true);

        return Map.of("message", "해당 게시글이 삭제되었습니다.");

    }
}
