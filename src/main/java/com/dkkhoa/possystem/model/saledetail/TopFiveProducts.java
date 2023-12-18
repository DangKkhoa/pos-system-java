package com.dkkhoa.possystem.model.saledetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TopFiveProducts {
    private String productName;
    private long quantitySold;
}
