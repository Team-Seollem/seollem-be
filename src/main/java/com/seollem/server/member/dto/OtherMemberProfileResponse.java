package com.seollem.server.member.dto;

import com.seollem.server.globaldto.PageInfo;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@NoArgsConstructor
public class OtherMemberProfileResponse<T> {

  private String name;
  private String url;
  private String content;
  private List<T> otherLibrary;
  private PageInfo pageInfo;

  public OtherMemberProfileResponse(String name, String url, String content, List<T> otherLibrary,
      PageInfo pageInfo) {
    this.name = name;
    this.url = url;
    this.content = content;
    this.otherLibrary = otherLibrary;
    this.pageInfo = pageInfo;
  }

  public OtherMemberProfileResponse(String name, String url, String content, List<T> otherLibrary,
      Page page) {
    this.name = name;
    this.url = url;
    this.content = content;
    this.otherLibrary = otherLibrary;
    this.pageInfo =
        new PageInfo(
            page.getNumber() + 1,
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages());
  }

}
