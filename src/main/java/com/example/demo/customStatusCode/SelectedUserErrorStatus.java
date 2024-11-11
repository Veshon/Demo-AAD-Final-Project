package com.example.demo.customStatusCode;

import com.example.demo.dto.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class SelectedUserErrorStatus implements UserStatus {
    private int statusCode;
    private String statusMessage;
}
