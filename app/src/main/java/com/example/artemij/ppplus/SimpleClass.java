package com.example.artemij.ppplus;

import lombok.Data;
import lombok.experimental.Builder;

/**
 * Created by Artemij on 15.11.2016.
 */

@Data
@Builder
public class SimpleClass {

    private int digit;
    private String name;
    private char c;

}
