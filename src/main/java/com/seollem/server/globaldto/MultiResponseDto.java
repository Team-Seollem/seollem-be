package com.seollem.server.globaldto;

import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class MultiResponseDto<T> {

  private final List<T> item;
  private final PageInfo pageInfo;

  public MultiResponseDto(List<T> item, Page page) {
    this.item = item;
    this.pageInfo =
        new PageInfo(
            page.getNumber() + 1,
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages());
  }
}
