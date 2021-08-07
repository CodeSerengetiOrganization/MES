package service;

import domain.ProcessEventEnum;
import domain.ProcessStateEnum;
import domain.ProductManufacturingStatus;
import org.springframework.messaging.Message;

import java.util.Map;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-03-27
 * @description :
 */
public interface ManufacturingStatusService {
    //2.Retrieve
    ProductManufacturingStatus findByBarcode(String barcode);
    //5. state machine related
    void updateManufacturingStatus(Map<String, String> msg);
    Boolean updateManufacturingStatus(String barcode, String newEvent);
    void updateManufacturingStatusMessage(Message<ProcessEventEnum> message);

    ProductManufacturingStatus saveStatusToDatabase(Message message);
    ProductManufacturingStatus saveStatus(String barcode, ProcessStateEnum targetStatus);

    ProductManufacturingStatus saveInitStatus(String barcode, ProcessStateEnum status);
}//ManufacturingStatusService
