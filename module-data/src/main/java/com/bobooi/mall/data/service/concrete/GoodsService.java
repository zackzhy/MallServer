package com.bobooi.mall.data.service.concrete;

import com.bobooi.mall.common.exception.ApplicationException;
import com.bobooi.mall.common.exception.AssertUtils;
import com.bobooi.mall.common.response.SystemCodeEnum;
import com.bobooi.mall.data.bo.PageParam;
import com.bobooi.mall.data.bo.ProductTypeBO;
import com.bobooi.mall.data.entity.product.*;
import com.bobooi.mall.data.repository.concrete.product.*;
import com.bobooi.mall.data.service.BaseDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 类描述
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/8/4
 */
@Service
@Slf4j
public class GoodsService extends BaseDataService<PdtInf, Integer> {
    @Resource
    PdtCategoryRepository pdtCategoryRepository;

    @Resource
    PdtTypeRepository pdtTypeRepository;

    @Resource
    PdtInfoRepository pdtInfoRepository;

    @Resource
    PdtAddiInfoRepository pdtAddiInfoRepository;

    @Resource
    PdtDetailViewRepository pdtDetailViewRepository;

    @Resource
    SupplierInfoRepository supplierInfoRepository;

    public List<PdtType> getAllProductType(PageParam pageParam) {
        return pdtTypeRepository.findAll(PageParam.getPageAble(pageParam)).getContent();
    }

    public long getAllProductTypeSum() {
        return pdtTypeRepository.count();
    }

    public List<ProductTypeBO> getProductTypeBOByProductId(Integer productId) {
        return pdtAddiInfoRepository.findAllByProductId(productId).stream().map(pdtAddiInf -> {
            Integer productTypeId = pdtAddiInf.getProductTypeId();
            String productTypeName = pdtTypeRepository.findByProductTypeId(productTypeId).getProductTypeName();
            return new ProductTypeBO(productTypeId, productTypeName);
        }).collect(Collectors.toList());
    }

    /**
     * 获取商品分类信息
     */
    public List<PdtCategory> getProductCategory() {
        return pdtCategoryRepository.findAll();
    }

    /**
     * 添加商品分类信息
     */
    public PdtCategory addProductCategory(String categoryName) {
        PdtCategory pdtCategory = pdtCategoryRepository.findByCategoryName(categoryName);
        AssertUtils.isNull(pdtCategory, new ApplicationException(SystemCodeEnum.ARGUMENT_WRONG, "该商品分类已存在"));
        return pdtCategoryRepository.save(new PdtCategory(null, categoryName));
    }

    /**
     * 删除商品分类信息
     */
    public void deleteProductCategory(Integer categoryId) {
        AssertUtils.notNull(pdtCategoryRepository.findByCategoryId(categoryId),
                new ApplicationException(SystemCodeEnum.ARGUMENT_WRONG, "该商品分类不存在"));
        pdtCategoryRepository.deleteById(categoryId);
    }

    /**
     * 修改商品分类信息
     */
    public PdtCategory updateProductCategory(PdtCategory pdtCategory) {
        PdtCategory pdtCategoryInDB = pdtCategoryRepository.findByCategoryId(pdtCategory.getCategoryId());
        AssertUtils.notNull(pdtCategoryInDB, new ApplicationException(SystemCodeEnum.ARGUMENT_WRONG, "该商品分类不存在"));
        pdtCategoryInDB.setCategoryName(pdtCategory.getCategoryName());
        return pdtCategoryRepository.save(pdtCategoryInDB);
    }

    public List<PdtInf> getNoSecPdtInfByCategoryId(Integer categoryId, PageParam pageParam) {
        return pdtInfoRepository.findAllByCategoryIdWithoutSec(categoryId, PageParam.getPageAble(pageParam)).getContent();
    }

    public List<PdtInf> getSecPdtInfByCategoryId( PageParam pageParam) {
        return pdtInfoRepository.findAllByCategoryIdWithSec(PageParam.getPageAble(pageParam)).getContent();
    }

    /**
     * 根据商品id获取商品详细信息列表
     *
     * @param productId 商品id
     * @return
     */
    public List<PdtDetailInf> getPdtDetailInfoByPdtId(Integer productId) {
        List<PdtDetailInf> pdtDetailInfList = pdtDetailViewRepository.findByProductId(productId);
        AssertUtils.notNull(pdtDetailInfList, new ApplicationException(SystemCodeEnum.ARGUMENT_WRONG, "该商品不存在或已经下架！"));
        return pdtDetailInfList;
    }

    public List<PdtDetailInf> getAllPdtDetailInfo(PageParam pageParam) {
        return pdtDetailViewRepository.findAll(PageParam.getPageAble(pageParam)).getContent();
    }

    public long getAllPdtDetailInfoSum() {
        return pdtDetailViewRepository.count();
    }

    public List<SupplierInf> getAllSupplierInfo(PageParam pageParam) {
        return supplierInfoRepository.findAll(PageParam.getPageAble(pageParam)).getContent();
    }

    public long getAllSupplierInfoSum() {
        return supplierInfoRepository.count();
    }

    /**
     * 添加商品供应商信息
     */
    public SupplierInf addSupplierInfo(String supplierName,String address) {
        SupplierInf supplierInf = supplierInfoRepository.findBySupplierNameAndAddress(supplierName,address);
        AssertUtils.isNull(supplierInf, new ApplicationException(SystemCodeEnum.ARGUMENT_WRONG, "该供应商信息已存在"));
        return supplierInfoRepository.save(new SupplierInf(null, supplierName,address));
    }

    /**
     * 修改商品供应商信息
     */
    public SupplierInf updateSupplierInf(SupplierInf supplierInf) {
        SupplierInf supplierInfInDB = supplierInfoRepository.findBySupplierId(supplierInf.getSupplierId());
        AssertUtils.notNull(supplierInfInDB, new ApplicationException(SystemCodeEnum.ARGUMENT_WRONG, "该供应商信息不存在"));
        supplierInfInDB.setSupplierName(supplierInf.getSupplierName());
        supplierInfInDB.setAddress(supplierInf.getAddress());
        return supplierInfoRepository.save(supplierInfInDB);
    }

    /**
     * 删除供应商信息
     */
    public void deleteSupplierInfo(Integer supplierId) {
        AssertUtils.notNull(supplierInfoRepository.findBySupplierId(supplierId),
                new ApplicationException(SystemCodeEnum.ARGUMENT_WRONG, "该供应商不存在"));
        supplierInfoRepository.deleteById(supplierId);
    }

    /**
     * 根据商品id删除商品所有信息
     *
     * @param productId 商品id
     * @return
     */
    public void deleteAllProductInfo(Integer productId) {
        pdtInfoRepository.deleteByProductId(productId);
        pdtAddiInfoRepository.deleteAllByProductId(productId);
    }

    /**
     * 更新商品信息
     *
     * @param productId
     * @param productName
     * @param description
     * @param picUrl
     * @param price
     * @param inventory
     * @param restTime
     * @return 返回是否成功（前端自己不要返回实体信息）
     */
    public boolean updateProductInfo(Integer productId, String productName, String description, String picUrl, Float price, Integer inventory, Timestamp restTime) {
        PdtInf pdtInf = pdtInfoRepository.getById(productId);
        AssertUtils.notNull(pdtInf, new ApplicationException(SystemCodeEnum.ARGUMENT_WRONG, "对应商品不存在！"));
        pdtInf.setProductName(productName);
        pdtInf.setDescription(description);
        pdtInf.setPicUrl(picUrl);
        pdtInf.setPrice(price);
        pdtInf.setInventory(inventory);
        pdtInf.setRestTime(restTime);
        return null != pdtInfoRepository.save(pdtInf);
    }

    /**
     * 更新商品信息
     *
     * @param pdtInf
     * @param productTypeId
     * @return 返回是否成功
     */
    public boolean addProduct(PdtInf pdtInf, Integer productTypeId) {
        PdtInf pdtInfInDB = this.save(pdtInf);
        AssertUtils.notNull(pdtInfInDB, new ApplicationException(SystemCodeEnum.SERVER_INNER_ERROR));
        AssertUtils.notNull(pdtTypeRepository.findById(productTypeId),new ApplicationException(SystemCodeEnum.ARGUMENT_WRONG,"商品类型id不存在"));
        return null!=pdtAddiInfoRepository.save(new PdtAddiInf(null, pdtInfInDB.getProductId(), productTypeId));
    }
}
