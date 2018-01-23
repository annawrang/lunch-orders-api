package com.rungway.domain;

import java.util.Map;
import org.springframework.data.annotation.Id;
import lombok.Data;
import lombok.NonNull;

@Data
public class Restaurant {

    @Id @NonNull
    private String slug;

    @NonNull
    private Map<String, String> orders;

}
