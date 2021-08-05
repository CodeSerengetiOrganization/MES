package src.main.java.util;

import com.google.common.base.Converter;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.mytech.domain.ProductionResult;
import com.mytech.dto.ProductionResultInputDTO;
import com.mytech.dto.ProductionResultOutputDTO;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-03-03
 * @description :
 */
public class ProductionResultUtils {

    private static final ProductionResultDTOConverter OUTPUT_DTO_CONVERTER =new ProductionResultDTOConverter();
    private static final ProductionResultInputDTOConverter INPUT_DTO_CONVERTER=new ProductionResultInputDTOConverter();

    public static List<ProductionResultOutputDTO> convertToOutputDTOList(List<ProductionResult> listPR) {
        if (listPR == null) return null;
        List<ProductionResultOutputDTO> list = Lists.newArrayList();
        ProductionResultOutputDTO outputDTO=new ProductionResultOutputDTO();
        for (ProductionResult pr:listPR) {
            //convert to DTO and put into list;
            outputDTO=ProductionResultUtils.convertToOutputDTO(pr);
//            System.out.println("hashcode of outputDOT after creating:"+outputDTO.hashCode());
            list.add(outputDTO);
        }//for-each
/*        for (ProductionResultOutputDTO dto:list) {
            System.out.println("index of ["+list.indexOf(dto)+"] hashcode in list:"+dto.hashCode());
        }*/
        return list;
    }//convertToDTOList

    public static List<ProductionResult> convertToProductionResultList(List<ProductionResultInputDTO> InputDTOList) {
        if (InputDTOList == null) return null;
        List<ProductionResult> list = Lists.newArrayList();
        ProductionResult result=new ProductionResult();
        for (ProductionResultInputDTO inDTO:InputDTOList) {
            //convert to DTO and put into list;
            result=ProductionResultUtils.convertToProductionResult(inDTO);
            list.add(result);
        }//for-each
        return list;
    }//convertToProductionResultList

    public static ProductionResultOutputDTO convertToOutputDTO(ProductionResult pr) {
        Preconditions.checkNotNull(pr,"the ProductionResult object is null;");
        return OUTPUT_DTO_CONVERTER.doForward(pr);
    }
    public static ProductionResult convertToProductionResult(ProductionResultInputDTO inputDTO){
//        ProductionResultInputDTOConverter converter=new ProductionResultInputDTOConverter();
        return INPUT_DTO_CONVERTER.doForward(inputDTO);
    }

    private static class ProductionResultDTOConverter extends Converter<ProductionResult, ProductionResultOutputDTO> {

        @Override
        protected  ProductionResultOutputDTO doForward(ProductionResult result) {
            Preconditions.checkNotNull(result,"the ProductionResult object is null;");
            ProductionResultOutputDTO outputDTO=new ProductionResultOutputDTO();
            BeanUtils.copyProperties(result,outputDTO);
            return  outputDTO;
        }//doForward

        @Override
        protected ProductionResult doBackward(ProductionResultOutputDTO productionResultOutputDTO) {
            throw new AssertionError("Converting ProductionResultOutputDTO to ProductionResult is not support!");
        }//doBackward
    }//ProductionResultDTOConverter

    private static class ProductionResultInputDTOConverter extends Converter<ProductionResultInputDTO,ProductionResult > {
        @Override
        protected ProductionResult doForward(ProductionResultInputDTO inputDTO) {
            ProductionResult pr=new ProductionResult();
            BeanUtils.copyProperties(inputDTO,pr);
            pr.setCreateTime(LocalDateTime.now());
            pr.setModifyTime(LocalDateTime.now());
            return pr;
        }

        @Override
        protected ProductionResultInputDTO doBackward(ProductionResult productionResult) {
            throw new AssertionError("Converting ProductionResultInputDTO to CoatingStationSetup is not support!");
        }

    }// class ProductionResultDTOConverter
}//ProductionResultUtils
