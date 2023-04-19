package com.seollem.server.globaldto;

import java.util.List;
import lombok.Getter;

@Getter
public class MultiResponseDtoForBooksHaveMemo<T> {

  private final List<T> item;
  private final PageInfo pageInfo;

  public MultiResponseDtoForBooksHaveMemo(List<T> item, PageInfo pageInfo) {
    this.item = item;
    this.pageInfo = pageInfo;
  }

}
