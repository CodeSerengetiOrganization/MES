package com.mytech;

import com.mytech.domain.ProcessEventEnum;
import com.mytech.dto.StateInputDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@SpringBootTest
public class StateInputDTOTests {
    LocalDateTime createTime= LocalDateTime.parse("2022-01-02T08:00");
    LocalDateTime modifyTime= LocalDateTime.parse("2022-01-02T08:00");

    @Test
    public void createStateInputDTOObject(){
        StateInputDTO stateInputDto = StateInputDTO.builder()
                .barcode("123")
                .event(ProcessEventEnum.LOAD_PASS)
                .stationName("LoadStation")
                .createTime(createTime)
                .modifyTime(modifyTime)
                .comment("created by test method")
                .build();
        System.out.println(stateInputDto);
    }

}//class

