package com.rbu.pms.rbu_pms.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OffersDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String offername;

    @NotNull
    @Size(max = 255)
    private String expdate;

    @NotNull
    @Size(max = 255)
    private String couponcode;

    @NotNull
    @Size(max = 255)
    private String percentage;

    @NotNull
    @Size(max = 255)
    private String maximum;

    private Long itemid;

}
