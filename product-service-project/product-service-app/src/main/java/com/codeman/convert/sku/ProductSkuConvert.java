package com.codeman.convert.sku;

import com.codeman.mysql.dataobject.sku.ProductSkuDO;
import com.codeman.rpc.sku.dto.ProductSkuRespDTO;
import com.codeman.service.sku.bo.ProductSkuBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author hdgaadd
 * Created on 2022/01/06
 */
@Mapper
public interface ProductSkuConvert {
    ProductSkuConvert INSTANCE = Mappers.getMapper(ProductSkuConvert.class);

    @Mapping(source = "attrs", target = "attrValueIds", qualifiedByName = "translateAttrValueIdsFromString")
    ProductSkuBO convert(ProductSkuDO bean);

    ProductSkuRespDTO convert(ProductSkuBO bean);

    @Named("translateAttrValueIdsFromString")
    default List<Integer> translateAttrValueIdsFromString(String str) {
        return StringUtils.splitToInt(str, ",");
    }
}
