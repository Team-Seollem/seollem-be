package com.seollem.server.globaldto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SingleResponseDto <T>{
    private T item;
}
