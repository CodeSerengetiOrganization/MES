package domain;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-03-25
 * @description :
 */
@Entity
@Table(name="t_product_manufacturing_status")
@Data
@Builder
public class ProductManufacturingStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="f_id")
    private Integer id;

    @Column(name="f_barcode")
    @NotNull(message = "barcode is null;")
    private String barcode;

    @Column(name = "f_order_number")
    @NotNull(message="order number is null;")
    String orderNumber;

    @Column(name="f_status")
    @NotNull(message = "manufacturing status is null")
    private String status;

    @Column(name="f_create_time")
    @NotNull(message ="createTime is null")
    private LocalDateTime createTime;

    @Column(name = "f_modify_time")
    @NotNull(message ="modifyTime is null")
    private LocalDateTime modifyTime;

    @Column(name = "f_comment")
    private String comment;

    @Tolerate
    public ProductManufacturingStatus(){};

}//ProductManufacturingStatus
