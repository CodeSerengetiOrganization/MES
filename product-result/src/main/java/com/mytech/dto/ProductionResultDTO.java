package com.mytech.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-01-19
 * @description :
 */
@Data
@SuperBuilder
public class ProductionResultDTO {

    @NotNull(message="barcode is null;")
    String barcode;

    @NotBlank(message = "station name is null or empty;")
    String stationName;

    @NotNull(message = "production result is null")
    Integer result;

//    @NotNull(message = "create time is null;")
    LocalDateTime createTime;

//    @NotNull(message = "modify time is null;")
    LocalDateTime modifyTime;
    String comment;

    @Tolerate
    public ProductionResultDTO(){}

}//ProductionResultDTO
