package com.seollem.server.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OtherLibraryDto {

  private long bookId;
  private String title;
  private String author;
  private String cover;
}
