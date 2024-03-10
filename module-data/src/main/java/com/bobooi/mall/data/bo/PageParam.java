package com.bobooi.mall.data.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * @author bobo
 * @date 2021/8/9
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageParam {
    private int pageNum;
    private int pageSize;

    public static Pageable getPageAble(PageParam pageParam){
        return PageRequest.of(pageParam.getPageNum()==0?0:pageParam.getPageNum()-1, pageParam.getPageSize());
    }
}
