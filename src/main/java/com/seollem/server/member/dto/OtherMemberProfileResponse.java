package com.seollem.server.member.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OtherMemberProfileResponse<T> {

  private String name;
  private String url;
  private String content;
  private List<T> otherLibrary;
}
