package com.seollem.server.member.dto.othermemberprofile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OtherMemberDto {

  private String name;
  private String url;
  private String content;
}