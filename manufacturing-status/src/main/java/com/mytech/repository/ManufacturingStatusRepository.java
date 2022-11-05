package com.mytech.repository;

import com.mytech.domain.ProductManufacturingStatus;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Optional;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-03-25
 * @description :
 */
@RequestScope
public interface ManufacturingStatusRepository extends JpaRepository<ProductManufacturingStatus, Integer> {
    //1.Create

    @Override
    <S extends ProductManufacturingStatus> S save(S s);

    //2.Retrieve

    @Override
    <S extends ProductManufacturingStatus> Optional<S> findOne(Example<S> example);
    ProductManufacturingStatus findByBarcode(String barcode);
//    findOneByString(String barcode);

    //3. Update
    //4. Delete
    void deleteByBarcode(String barcode);

}//ManufacturingStatusRepository

