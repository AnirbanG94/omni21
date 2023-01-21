package com.omnichannel.app.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class OpenStockDTO {
    private Integer articleId;
    private Integer uomId;
    private Double qty;
    private Double val;
}
