package com.rbu.pms.rbu_pms.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MultimediaDTO {

    private Long id;

    @NotNull
    @Size(max = 2000)
    private String primary;

    @NotNull
    @Size(max = 5000)
    private String secondary;

    @NotNull
    private Boolean isactive;

    private Long itemid;

}
