package com.project.micoserivce.order.model;

import java.math.BigDecimal;

public record OrderRequest(Long id, String orderName, String skuCode,
                           BigDecimal price, Integer quantity) {
}
