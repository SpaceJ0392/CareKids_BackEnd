package com.aivle.carekids.domain.question.service;

import com.aivle.carekids.domain.question.dto.QuestionFileDto;
import com.aivle.carekids.domain.question.models.Question;
import com.aivle.carekids.domain.question.models.QuestionFile;
import com.aivle.carekids.domain.question.repository.QuestionFileRepository;
import com.aivle.carekids.domain.user.models.Users;
import com.aivle.carekids.global.Variable.GlobelVar;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {

    @Value("${file.upload-path}")
    private String BASE_PATH;
    private static final String NOTICE_IMG_PATH = "";


    private final QuestionFileRepository questionFileRepository;
    private final ModelMapper entityModelMapper;
    private final ResourceLoader resourceLoader;

    @Transactional
    public void saveFile(Question targetQuestion, List<MultipartFile> questionFiles, String usersNickname) {

        if (questionFiles.isEmpty()) { return; }

        List<QuestionFile> targetQuestionFile = new ArrayList<>();

        questionFiles.forEach(file -> {
            QuestionFileDto questionFileDto = uploadFile(usersNickname, file);
            QuestionFile questionFile = entityModelMapper.map(questionFileDto, QuestionFile.class);
            questionFile.setQuestionInfo(targetQuestion);
            targetQuestionFile.add(questionFile);
        });

        questionFileRepository.saveAll(targetQuestionFile);
    }

    @Transactional
    public void updateFile(Question targetQuestion, Users users, List<MultipartFile> multipartFiles){

        List<QuestionFile> targetFiles = questionFileRepository.findByQuestion(targetQuestion);
        List<QuestionFile> targetQuestionFile = new ArrayList<>();

        targetFiles.forEach(file -> {file.setDeletedInfo(true);});

        List<QuestionFile> newQuestionFiles = new ArrayList<>();
        multipartFiles.forEach(file -> {
            QuestionFileDto questionFileDto = uploadFile(users.getUsersNickname(), file);
            QuestionFile questionFile = entityModelMapper.map(questionFileDto, QuestionFile.class);
            questionFile.setQuestionInfo(targetQuestion);
            newQuestionFiles.add(questionFile);
        });

        if (!newQuestionFiles.isEmpty()) { questionFileRepository.saveAll(newQuestionFiles); }
    }

    public QuestionFileDto uploadFile(String usersNickname, MultipartFile file){

        String originalName = file.getOriginalFilename();
        String mimeType = file.getContentType();

        UUID uuid = UUID.randomUUID();
        assert originalName != null;
        String saveName = uuid.toString() + "_" + originalName;

        Path filePath = Paths.get(BASE_PATH + File.separator + usersNickname + File.separator + saveName);
        Path parent = filePath.getParent();
        try {
            if (!Files.exists(filePath.getParent())){
                Files.createDirectories(filePath.getParent());
            }

            Files.copy(file.getInputStream(), filePath);
        } catch (Exception e) { return null; }

        return new QuestionFileDto(originalName, saveName, filePath.toString());
    }


    public String uploadFileNotice(MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) { return null; }

        String originalName = file.getOriginalFilename();
        String mimeType = file.getContentType();
        if (mimeType != null && !mimeType.startsWith("image/")) { return null; }


        UUID uuid = UUID.randomUUID();
        assert originalName != null;
        String saveName = uuid.toString() + "_" + originalName;

        Resource resource = resourceLoader.getResource("classpath:static/images");
        Path resourcePath = Paths.get(resource.getURI());
        Path filePath = resourcePath.resolve("notice").resolve(saveName);

        try {
            if (!Files.exists(filePath.getParent())){
                Files.createDirectories(filePath.getParent());
            }
            Files.copy(file.getInputStream(), filePath);
        } catch (Exception e) { return null; }

        String targetImg = filePath.subpath(resourcePath.getNameCount(), filePath.getNameCount())
                .toString().replaceAll("\\\\", "/");
        return GlobelVar.SERVER_BASE_URL + "/images/" + targetImg;
    }
}
