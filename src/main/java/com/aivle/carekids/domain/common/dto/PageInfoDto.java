package com.aivle.carekids.domain.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
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

        @JsonProperty("count")
        private int count;
    }

    private PageInfo pageInfo;
    private List<?> pageList;
}

