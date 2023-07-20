package org.example.DTO;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyInfo {
    String id;
    String numCode;
    String charCode;
    String nominal;
    String name;
    Double value;
}
