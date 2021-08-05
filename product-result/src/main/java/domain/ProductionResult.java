package src.main.java.domain;


import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-03-05
 * @description : this is a util for multi thread purpose: when create a runnable instance with 'new'
 * key word, it is not managed by spring framework, so '@Autowired' annotation won't work,
 * need to use this util to get instance such as Service or Repository.
 */

@Data
@Builder
@Entity
//@Table(name="t_production_result_scu20210305")
@Table(name="t_production_result")
public class ProductionResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="f_id")
    Integer id;

    @Column(name="f_barcode")
    @NotNull(message="barcode is null;")
    String barcode;

    @Column(name = "f_order_number")
    @NotNull(message="order number is null;")
    String orderNumber;

    @Column(name="f_station_name")
    @NotBlank(message = "station name is null or empty;")
    String stationName;

    @Column(name="f_result")
    @NotNull(message = "production result is null")
    Integer result;

    @Column(name ="f_create_time")
    @NotNull(message = "create time is null;")
    LocalDateTime createTime;

    @Column(name="f_modify_time")
    @NotNull(message = "modify time is null;")
    LocalDateTime modifyTime;

    @Column(name="f_comment")
    String comment;

    @Tolerate
    public ProductionResult(){}
}
