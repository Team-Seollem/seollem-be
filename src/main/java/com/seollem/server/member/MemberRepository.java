package com.seollem.server.member;

import com.seollem.server.member.dto.HallOfFameInnerDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByEmail(String email);

  @Query(value = "select "
      + "new com.seollem.server.member.dto.HallOfFameInnerDto(m.memberId, m.url, m.name, count(b.bookId)) "
      + "from Member m " + "left outer join Book b " + "on m = b.member " + "group by m.memberId "
      + "order by count(b.bookId) desc")
  List<HallOfFameInnerDto> findHallOfFameWithBook(Pageable pageable);


  @Query(value = "select "
      + "new com.seollem.server.member.dto.HallOfFameInnerDto(m1.memberId, m1.url, m1.name, count(m2.memoId)) "
      + "from Member m1 " + "left outer join Memo m2 " + "on m1 = m2.member "
      + "group by m1.memberId "
      + "order by count(m2.memoId) desc")
  List<HallOfFameInnerDto> findHallOfFameWithMemo(Pageable pageable);

}
