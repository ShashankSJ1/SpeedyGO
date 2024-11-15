package com.happiest.BookingService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransporterInfoDTO {
    private String email;
    private String username;
    private Long phoneNumber;

}

