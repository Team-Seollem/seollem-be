package com.seollem.server.globaldto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class MultiResponseDto <T> {
    private List<T> item;
    private PageInfo pageInfo;

    public MultiResponseDto(List<T> item, Page page){
        this.item = item;
        this.pageInfo =new PageInfo(page.getNumber() +1 ,
                page.getSize(),page.getTotalElements(),page.getTotalPages());
    }

}
