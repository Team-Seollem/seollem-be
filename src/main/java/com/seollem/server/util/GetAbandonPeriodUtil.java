package com.seollem.server.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component
public class GetAbandonPeriodUtil {

  public ArrayList<LocalDateTime> getAbandonPeriod(int year, int month) {
    LocalDate newDate = LocalDate.of(year, month, 1); // 마지막 일자는 형식상 넣어주는 것 뿐 의미없음.
    int lengthOfMon = newDate.lengthOfMonth();

    LocalDateTime before = LocalDateTime.of(year, month, 1, 0, 0, 1);
    LocalDateTime after = LocalDateTime.of(year, month, lengthOfMon, 23, 59, 59);

    ArrayList<LocalDateTime> result = new ArrayList<LocalDateTime>();
    result.add(before);
    result.add(after);

    return result;

  }

}
