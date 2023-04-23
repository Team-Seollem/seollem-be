package com.seollem.server.member;

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
      + "new com.seollem.server.member.HallOfFameInnerDto(m.memberId, m.url, m.name, count(b.bookId)) "
      + "from Member m " + "left outer join Book b " + "on m = b.member " + "group by m.memberId "
      + "order by count(b.bookId) desc")
  List<HallOfFameInnerDto> findHallOfFameWithBook(Pageable pageable);


}
