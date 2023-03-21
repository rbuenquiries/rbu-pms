package com.rbu.pms.rbu_pms.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ItemsDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String code;

    @NotNull
    private Double price;

    @Size(max = 255)
    private String description;

    @Size(max = 255)
    private String color;

    @Size(max = 255)
    private String weight;

    private Boolean isactive;

    private Long brandid;

}
