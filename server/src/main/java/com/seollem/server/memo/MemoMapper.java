package com.seollem.server.memo;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemoMapper {
    Memo memoPostToMemo(MemoDto.Post post);
    Memo memoPatchToMemo(MemoDto.Patch patch);

    MemoDto.RandomResponse momoToMemoRandom(Memo memo);
    MemoDto.Response memoToMemoResponse(Memo memo);
    List<MemoDto.Response> memoToMemoResponses(List<Memo> memos);




}
