package com.seollem.server.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HallOfFameInnerDto {

  private long memberId;
  private String url;
  private String name;
  private long count;

  public HallOfFameInnerDto() {
  }

  public HallOfFameInnerDto(long memberId, String url, String name, long count) {
    this.memberId = memberId;
    this.url = url;
    this.name = name;
    this.count = count;
  }
}