package com.seollem.server.globaldto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PageInfo {

  private int page;
  private int size;
  private long totalElements;
  private int totalPages;
}
