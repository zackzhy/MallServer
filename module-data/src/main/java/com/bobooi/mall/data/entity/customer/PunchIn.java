package com.bobooi.mall.data.entity.customer;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author bobo
 * @date 2021/4/20
 */

@Data
@Entity
@NoArgsConstructor
public class PunchIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer punchInId;
    private Integer customerId;
    private Integer year;
    private Integer month;
    private Integer dailyBitmap;

    public PunchIn(Integer customerId, Integer year, Integer month, Integer dailyBitmap) {
        this.customerId = customerId;
        this.year = year;
        this.month = month;
        this.dailyBitmap = dailyBitmap;
    }
}
