package com.aivle.carekids.domain.common.service;

import com.aivle.carekids.domain.common.dto.*;
import com.aivle.carekids.domain.common.repository.AgeTagRepository;
import com.aivle.carekids.domain.common.repository.RegionRepository;
import com.aivle.carekids.domain.place.dto.PlaceMaincateDto;
import com.aivle.carekids.domain.place.dto.PlaceSubcateDto;
import com.aivle.carekids.domain.place.repository.PlaceMaincateRepository;
import com.aivle.carekids.domain.place.repository.PlaceSubcateRepository;
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
    private final PlaceMaincateRepository placeMaincateRepository;
    private final PlaceSubcateRepository placeSubcateRepository;

    private final ModelMapper dtoModelMapper;

    public RegionAgeTagListDto regionAgeTagAll() {

        List<RegionDto> regions = regionRepository.findAll().stream()
                .map(r -> dtoModelMapper.map(r, RegionDto.class)).toList();

        List<AgeTagDto> ageTags = ageTagRepository.findAll().stream()
                .map(a -> dtoModelMapper.map(a, AgeTagDto.class)).toList();


        return new RegionAgeTagListDto(ageTags,regions);
    }

    public RegionSubcateListDto regionSubcateAll() {

        List<RegionDto> regions = regionRepository.findAll().stream()
                .map(r -> dtoModelMapper.map(r, RegionDto.class)).toList();

        List<PlaceSubcateDto> categories = placeSubcateRepository.findAll().stream()
                .map(a -> dtoModelMapper.map(a, PlaceSubcateDto.class)).toList();

        return new RegionSubcateListDto(regions, categories);
    }

    public RegionMaincateListDto regionMaincateAll() {

        List<RegionDto> regions = regionRepository.findAll().stream()
                .map(r -> dtoModelMapper.map(r, RegionDto.class)).toList();

        List<PlaceMaincateDto> categories = placeMaincateRepository.findAll().stream()
                .map(a -> dtoModelMapper.map(a, PlaceMaincateDto.class)).toList();

        return new RegionMaincateListDto(regions, categories);
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
