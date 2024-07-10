package com.aivle.carekids.domain.common.service;

import com.aivle.carekids.domain.common.dto.AgeTagDto;
import com.aivle.carekids.domain.common.dto.RegionAgeTagListDto;
import com.aivle.carekids.domain.common.dto.RegionDto;
import com.aivle.carekids.domain.common.repository.AgeTagRepository;
import com.aivle.carekids.domain.common.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommonService {

    private final RegionRepository regionRepository;
    private final AgeTagRepository ageTagRepository;

    private final ModelMapper dtoModelMapper;

    public RegionAgeTagListDto regionAgeTagAll() {

        List<RegionDto> regions = regionRepository.findAll().stream()
                .map(r -> dtoModelMapper.map(r, RegionDto.class)).toList();

        List<AgeTagDto> ageTags = ageTagRepository.findAll().stream()
                .map(a -> dtoModelMapper.map(a, AgeTagDto.class)).toList();


        return new RegionAgeTagListDto(ageTags,regions);
    }

    public List<RegionDto> regionAll(){
        return regionRepository.findAll().stream()
                .map(r -> dtoModelMapper.map(r, RegionDto.class)).toList();
    }

    public List<AgeTagDto> ageTagAll(){
        return ageTagRepository.findAll().stream()
                .map(a -> dtoModelMapper.map(a, AgeTagDto.class)).toList();
    }
}
