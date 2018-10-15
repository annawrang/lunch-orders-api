package com.rungway.domain;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Restaurant {

    @Id
    @NonNull
    private String slug;

    @NonNull
    private List<Order> orders;
}
