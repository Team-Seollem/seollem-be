package com.seollem.server.memo;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemoMapper {

  Memo memoPostToMemo(MemoDto.Post post);

  Memo memoPatchToMemo(MemoDto.Patch patch);

  MemoDto.RandomResponse momoToMemoRandom(Memo memo);

  MemoDto.Response memoToMemoResponse(Memo memo);

  List<MemoDto.Response> memoToMemoResponses(List<Memo> memos);
}
