package com.aivle.carekids.domain.place.service;

import com.aivle.carekids.domain.common.dto.*;
import com.aivle.carekids.domain.common.repository.RegionRepository;
import com.aivle.carekids.domain.kindergarten.dto.KindergartenDetailDto;
import com.aivle.carekids.domain.kindergarten.dto.KindergartenListDto;
import com.aivle.carekids.domain.place.dto.PlaceDetailDto;
import com.aivle.carekids.domain.place.dto.PlaceListDto;
import com.aivle.carekids.domain.place.dto.PlaceMaincateDto;
import com.aivle.carekids.domain.place.repository.PlaceMaincateRepository;
import com.aivle.carekids.domain.place.repository.PlaceRepository;
import com.aivle.carekids.domain.user.dto.UsersDetailDto;
import com.aivle.carekids.domain.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceMaincateRepository placeMaincateRepository;
    private final UsersRepository usersRepository;
    private final RegionRepository regionRepository;

    private final ModelMapper dtoModelMapper;

    public PlacePageInfoDto displayPlaceGuest(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        RegionDto regionDto = dtoModelMapper.map(regionRepository.findByRegionName("전체"), RegionDto.class);
        PlaceMaincateDto placeMaincateDto = dtoModelMapper.map(placeMaincateRepository.findByPlaceMaincateName("전체"), PlaceMaincateDto.class);
        Page<PlaceListDto> placePage = placeRepository.findAllByOrderByUpdatedAtDescByPageByRegionMainCate(null, null, pageable);

        return new PlacePageInfoDto(new PlacePageInfoDto.PageInfo(
                placePage.getTotalPages(),
                placePage.getNumber() + 1,
                placePage.getSize(),
                placePage.getNumberOfElements()
        ), regionDto, placeMaincateDto, placePage.getContent());
    }

    public PlacePageInfoDto displayPlaceAdmin(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        RegionDto regionDto = dtoModelMapper.map(regionRepository.findByRegionName("전체"), RegionDto.class);
        PlaceMaincateDto placeMaincateDto = dtoModelMapper.map(placeMaincateRepository.findByPlaceMaincateName("전체"), PlaceMaincateDto.class);

        Page<PlaceDetailDto> placePage = placeRepository.findAllByOrderByUpdatedAtDescByPageByRegionMainCateAdmin(null, null, pageable);

        return new PlacePageInfoDto(new PlacePageInfoDto.PageInfo(
                placePage.getTotalPages(),
                placePage.getNumber() + 1,
                placePage.getSize(),
                placePage.getNumberOfElements()
        ), regionDto, placeMaincateDto, placePage.getContent());
    }

    public PlacePageInfoDto displayPlaceUser(Long usersId, int page, int size) {

        Optional<UsersDetailDto> users = usersRepository.findUsersDetailWithRegionAndKids(usersId);
        if (users.isEmpty()){ return null; }

        Pageable pageable = PageRequest.of(page, size);
        RegionDto usersRegion = users.get().getUsersRegion();

        PlaceMaincateDto placeMaincateDto = dtoModelMapper.map(placeMaincateRepository.findByPlaceMaincateName("전체"), PlaceMaincateDto.class);
        Page<PlaceListDto> placePage = placeRepository.findAllByOrderByUpdatedAtDescByPageByRegionMainCate(usersRegion.getRegionId(), null, pageable);

        return new PlacePageInfoDto(new PlacePageInfoDto.PageInfo(
                placePage.getTotalPages(),
                placePage.getNumber() + 1,
                placePage.getSize(),
                placePage.getNumberOfElements()
        ), users.get().getUsersRegion(), placeMaincateDto, placePage.getContent());
    }

    public PlaceDetailDto placeDetail(Long placeId) {

        if (!placeRepository.existsById(placeId)) { return null; }
        return placeRepository.findPlaceDetail(placeId);
    }

    public PlacePageInfoDto searchPlace(SearchRegionCateDto searchRegionCateDto, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<PlaceListDto> searchPlaceListDtos = placeRepository.searchPlaceByFilter(searchRegionCateDto, pageable);

        return new PlacePageInfoDto(new PlacePageInfoDto.PageInfo(
                searchPlaceListDtos.getTotalPages(),
                searchPlaceListDtos.getNumber() + 1,
                searchPlaceListDtos.getSize(),
                searchPlaceListDtos.getNumberOfElements()
        ), searchRegionCateDto.getRegionDto(), searchRegionCateDto.getMaincateDto(), searchPlaceListDtos.getContent());
    }

}
