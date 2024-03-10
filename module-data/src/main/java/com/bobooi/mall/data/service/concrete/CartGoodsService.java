package com.bobooi.mall.data.service.concrete;

import com.bobooi.mall.common.exception.ApplicationException;
import com.bobooi.mall.common.exception.AssertUtils;
import com.bobooi.mall.common.response.SystemCodeEnum;
import com.bobooi.mall.data.bo.CartGoodsBO;
import com.bobooi.mall.data.bo.PageParam;
import com.bobooi.mall.data.entity.product.CartGoods;
import com.bobooi.mall.data.entity.product.PdtAddiInf;
import com.bobooi.mall.data.entity.product.PdtInf;
import com.bobooi.mall.data.entity.product.PdtType;
import com.bobooi.mall.data.repository.concrete.product.CartGoodsRepository;
import com.bobooi.mall.data.repository.concrete.product.PdtAddiInfoRepository;
import com.bobooi.mall.data.repository.concrete.product.PdtInfoRepository;
import com.bobooi.mall.data.repository.concrete.product.PdtTypeRepository;
import com.bobooi.mall.data.service.BaseDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author bobo
 * @date 2021/8/4
 */

@Service
@Slf4j
public class CartGoodsService extends BaseDataService<CartGoods, Integer> {
    @Resource
    private PdtInfoRepository pdtInfoRepository;
    @Resource
    private CartGoodsRepository cartGoodsRepository;
    @Resource
    private PdtTypeRepository pdtTypeRepository;
    @Resource
    private PdtAddiInfoRepository pdtAddiInfoRepository;

    public CartGoods addCartGoods(Integer customerId, Integer productId, Integer productAmount, Integer productTypeId){
        PdtInf pdtInf = pdtInfoRepository.getById(productId);
        PdtAddiInf pdtAddiInf = pdtAddiInfoRepository.findByProductIdAndProductTypeId(productId, productTypeId);
        AssertUtils.notNull(pdtAddiInf,new ApplicationException(SystemCodeEnum.ARGUMENT_WRONG,"对应商品不存在！"));
        AssertUtils.isFalse(pdtInf.getInventory()<productAmount,new ApplicationException(SystemCodeEnum.ARGUMENT_WRONG,"超出库存数量！"));
        CartGoods cartGoods = cartGoodsRepository.findByCustomerIdAndProductIdAndProductTypeId(customerId,productId,productTypeId);
        if(cartGoods==null){
            cartGoods = this.insert(new CartGoods(null,customerId,productId,productAmount,productTypeId));
        }else{
            cartGoods.setProductAmount(cartGoods.getProductAmount()+productAmount);
            cartGoods = this.save(cartGoods);
        }
        return cartGoods;
    }

    public List<CartGoodsBO> getCartGoodsList(Integer customerId, PageParam pageParam){
        return cartGoodsRepository.findAllByCustomerId(customerId, PageParam.getPageAble(pageParam)).getContent()
                .stream().map(cartGoods->{
                    PdtInf pdtInf = pdtInfoRepository.findByProductId(cartGoods.getProductId());
                    PdtType pdtType = pdtTypeRepository.getById(cartGoods.getProductTypeId());
                    return new CartGoodsBO(cartGoods,pdtInf,pdtType);
                }).collect(Collectors.toList());
    }

    public void deleteByCartGoodsId(Integer cartGoodsId){
        cartGoodsRepository.deleteByCartGoodsId(cartGoodsId);
    }

    public void clear(Integer customerId){
        cartGoodsRepository.deleteAllByCustomerId(customerId);
    }
}
