package com.aivle.carekids.domain.question.service;

import com.aivle.carekids.domain.common.dto.PageInfoDto;
import com.aivle.carekids.domain.question.dto.QuestionDetailDisplayDto;
import com.aivle.carekids.domain.question.dto.QuestionDetailDto;
import com.aivle.carekids.domain.question.dto.QuestionFileDto;
import com.aivle.carekids.domain.question.dto.QuestionListDto;
import com.aivle.carekids.domain.question.models.Question;
import com.aivle.carekids.domain.question.repository.QuestionFileRepository;
import com.aivle.carekids.domain.question.repository.QuestionRepository;
import com.aivle.carekids.domain.user.dto.UsersLightDto;
import com.aivle.carekids.domain.user.models.Role;
import com.aivle.carekids.domain.user.models.Users;
import com.aivle.carekids.domain.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {

    private final UsersRepository usersRepository;
    private final QuestionRepository questionRepository;
    private final QuestionFileRepository questionFileRepository;
    private final ModelMapper dtoModelMapper;

    private final FileService fileService;

    public PageInfoDto displayQuestion(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Question> questionPage = questionRepository.findAllByOrderByUpdatedAtDesc(pageable);
        List<QuestionListDto> questionListDtoList = questionPage.map(q ->
             new QuestionListDto(q.getCreatedAt(), q.getUpdatedAt(), q.getQuestionId(), q.getQuestionTitle(), q.getSecret(), q.getQuestionCheck(),
             dtoModelMapper.map(q.getUsers(), UsersLightDto.class)
        )).toList();


        return new PageInfoDto(new PageInfoDto.PageInfo(questionPage.getTotalPages(), page + 1, size, questionPage.getNumberOfElements()),
                questionListDtoList);
    }

    @Transactional
    public Map<String, String> editQuestion(QuestionDetailDto questionDetailDto, List<MultipartFile> multipartFiles, Long usersId) {

        Optional<Users> users = usersRepository.findByUsersId(usersId);
        if (users.isEmpty()) { return null; }

        if (multipartFiles == null) { multipartFiles = new ArrayList<>(); }

        if (questionDetailDto.getQuestionId() != null){
            //게시글 수정
            Optional<Question> targetQuestion = questionRepository.findById(questionDetailDto.getQuestionId());
            if (targetQuestion.isEmpty()) { return Map.of("message", "존재하지 않는 게시글입니다."); }
            targetQuestion.get().updateQuestion(questionDetailDto);

            //파일도 수정
            fileService.updateFile(targetQuestion.get(), users.get(), multipartFiles);
            return Map.of("message", "수정이 완료되었습니다.");
        }

        //게시글 작성 후 저장
        Question newQuestion = Question.createQuestion(questionDetailDto);
        newQuestion.setUsersInfo(users.get());
        Question saveQuestion = questionRepository.save(newQuestion);

        //파일 저장
        if (multipartFiles != null) {
                fileService.saveFile(saveQuestion, multipartFiles, users.get().getUsersNickname());
        }

        return Map.of("message", "게시글이 생성되었습니다.");
    }

    public QuestionDetailDisplayDto displayQuestionDetail(Long questionId, Long usersId) {

        Optional<Question> targetQuestion = questionRepository.findById(questionId);
        Optional<Users> users = usersRepository.findByUsersId(usersId);

        // 유저여야 하고, 질문이 있어야 함.
        if (targetQuestion.isEmpty() || users.isEmpty()) { return null; }

        // 비밀 글이라면, 작성자하고, 관리자만 접근 가능.
        if (targetQuestion.get().getSecret() &&
                !(targetQuestion.get().getUsers() != users.get() || users.get().getUsersRole() != Role.ADMIN)){

            return null;
        }

        QuestionDetailDto questionDetail = dtoModelMapper.map(targetQuestion.get(), QuestionDetailDto.class);
        List<QuestionFileDto> fileOfQuestion = questionFileRepository.findByQuestion(targetQuestion.get())
                .stream().map((element) -> dtoModelMapper.map(element, QuestionFileDto.class)).toList();

        return new QuestionDetailDisplayDto(questionDetail, fileOfQuestion);
    }

    @Transactional
    public Map<String, String> deleteQuestion(Long questionId, Long usersId) {

        Optional<Question> targetQuestion = questionRepository.findById(questionId);
        Optional<Users> users = usersRepository.findByUsersId(usersId);

        if (targetQuestion.isEmpty() || users.isEmpty()) { return null; }
        if (targetQuestion.get().getUsers() != users.get()){ return null; }

        targetQuestion.get().setDeletedInfo(true);

        return Map.of("message", "해당 게시글이 삭제되었습니다.");
    }
}
