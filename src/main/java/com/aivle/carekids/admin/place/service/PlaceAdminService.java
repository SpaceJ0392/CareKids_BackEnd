package com.aivle.carekids.admin.place.service;

import com.aivle.carekids.domain.common.models.Region;
import com.aivle.carekids.domain.place.dto.PlaceDetailDto;
import com.aivle.carekids.domain.place.model.*;
import com.aivle.carekids.domain.place.repository.PlaceCateRepository;
import com.aivle.carekids.domain.place.repository.PlaceKeywordRepository;
import com.aivle.carekids.domain.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlaceAdminService {

    private final PlaceRepository placeRepository;
    private final PlaceCateRepository placeCateRepository;
    private final PlaceKeywordRepository placeKeywordRepository;
    private final ModelMapper entityModelMapper;


    @Transactional
    public ResponseEntity<?> editPlace(PlaceDetailDto placeDetailDto) {

        Region targetRegion = entityModelMapper.map(placeDetailDto.getRegion(), Region.class);
        List<PlaceCate> placeCateList = new ArrayList<>();
        List<PlaceKeyword> placeKeywordList = new ArrayList<>();

        if (placeDetailDto.getPlaceId() == null) {
            Place newPlace = Place.createNewPlace(placeDetailDto);
            newPlace.setRegionInfo(targetRegion);
            placeRepository.save(newPlace);

            PlaceSubcate targetSubcate = entityModelMapper.map(placeDetailDto.getPlaceSubcate(), PlaceSubcate.class);
            PlaceCate newPlaceCate = PlaceCate.createNewPlaceCate(newPlace, targetSubcate);
            newPlaceCate.setPlaceCateInfo(newPlace, targetSubcate);
            placeCateList.add(newPlaceCate);

            placeCateRepository.saveAll(placeCateList);

            placeDetailDto.getPlaceKeywords().forEach(keyword -> {
                Keyword targetKeyword = entityModelMapper.map(keyword, Keyword.class);
                PlaceKeyword newPlaceKeyword = PlaceKeyword.createNewPlaceKeyword(newPlace, targetKeyword);
                newPlaceKeyword.setPlaceKeywordInfo(newPlace, targetKeyword);
                placeKeywordList.add(newPlaceKeyword);
            });

            placeKeywordRepository.saveAll(placeKeywordList);

            return ResponseEntity.ok(Map.of("message", "장소 정보가 등록되었습니다."));
        }

        Optional<Place> targetPlace = placeRepository.findById(placeDetailDto.getPlaceId());
        if (targetPlace.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("not-found", "해당 장소 정보를 찾을 수 없습니다."));
        }

        targetPlace.get().updatePlaceInfo(placeDetailDto);
        targetPlace.get().setRegionInfo(targetRegion);
        targetPlace.get().clearPlaceCates();
        targetPlace.get().clearPlaceKeywords();

        //지역 추가
        PlaceSubcate targetSubcate = entityModelMapper.map(placeDetailDto.getPlaceSubcate(), PlaceSubcate.class);
        PlaceCate newPlaceCate = PlaceCate.createNewPlaceCate(targetPlace.get(), targetSubcate);
        newPlaceCate.setPlaceCateInfo(targetPlace.get(), targetSubcate);
        placeCateList.add(newPlaceCate);

        placeCateRepository.saveAll(placeCateList);

        //연령대 추가
        placeDetailDto.getPlaceKeywords().forEach(keyword -> {
            Keyword targetKeyword = entityModelMapper.map(keyword, Keyword.class);
            PlaceKeyword newPlaceKeyword = PlaceKeyword.createNewPlaceKeyword(targetPlace.get(), targetKeyword);
            newPlaceKeyword.setPlaceKeywordInfo(targetPlace.get(), targetKeyword);
            placeKeywordList.add(newPlaceKeyword);
        });

        placeKeywordRepository.saveAll(placeKeywordList);

        return ResponseEntity.ok(Map.of("message", "장소 정보가 수정되었습니다."));
    }


    @Transactional
    public ResponseEntity<?> deletePlace(Long placeId) {
        Optional<Place> targetPlace = placeRepository.findById(placeId);
        if (targetPlace.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("not-found", "장소 정보를 찾을 수 없습니다."));
        }

        targetPlace.get().deletedPlace(true);
        return ResponseEntity.ok(Map.of("message", "장소 정보가 삭제되었습니다."));
    }
}