package org.yakushev.shopwebapp.dto;

import lombok.Data;

@Data
public class TransactionRequest {
    private String description;
    private Long productId;
    private Long userId;
}
