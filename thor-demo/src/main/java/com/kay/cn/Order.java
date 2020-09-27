package com.kay.cn;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Order implements Serializable {
    private Long id;
    private String name;
    private BigDecimal price;
}
