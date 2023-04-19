package com.seollem.server.memo;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemoMapper {

  Memo memoPostToMemo(MemoDto.Post post);

  Memo memoPatchToMemo(MemoDto.Patch patch);

  MemoDto.RandomResponse memoToRandomMemoResponse(Memo memo);

  @Mapping(target = "memoLikesCount", ignore = true)
  MemoDto.Response memoToMemoResponse(Memo memo);

  MemoDto.PostResponse memoToMemoPostResponse(Memo memo);


  @Mapping(target = "memoLikesCount", ignore = true)
  List<MemoDto.Response> memoToMemoResponses(List<Memo> memos);
}
