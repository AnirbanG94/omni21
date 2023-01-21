package com.omnichannel.app.model.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VendorManufacturerDTO {
    private Integer vendorId;
    private List<Integer>manufactureId;
}
