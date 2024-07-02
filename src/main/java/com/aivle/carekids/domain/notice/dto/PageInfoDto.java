package com.aivle.carekids.domain.notice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter @Getter
@AllArgsConstructor
public class PageInfoDto {

    @AllArgsConstructor
    public static class PageInfo {
        @JsonProperty("total")
        private int totalPage;

        @JsonProperty("page")
        private int page;

        @JsonProperty("size")
        private int size;
    }

    private PageInfo pageInfo;
    private List<NoticeListDto> pageList;
}
