package com.rungway.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class Order {

    @NonNull
    private String name;

    @NonNull
    private String dish;
}
