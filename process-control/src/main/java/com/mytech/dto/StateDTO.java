package com.mytech.dto;

import com.mytech.domain.ProcessEventEnum;
import com.mytech.domain.ProcessStateEnum;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@SuperBuilder
public class StateDTO {
    @NotNull(message="barcode is null;")
    String barcode;

    @NotNull(message = "the event of product is null;")
    ProcessEventEnum event;

    @NotBlank(message = "station name is null or empty;")
    String stationName;

//
//    @NotNull(message = "production result is null")
//    Integer result;
//
//        @NotNull(message = "create time is null;")
    LocalDateTime createTime;

    //    @NotNull(message = "modify time is null;")
    LocalDateTime modifyTime;

    String comment;

    @Tolerate
    public StateDTO(){}
}
