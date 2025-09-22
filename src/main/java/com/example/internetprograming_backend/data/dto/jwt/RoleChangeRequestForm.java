package com.example.internetprograming_backend.data.dto.jwt;

import com.example.internetprograming_backend.common.type.TokenRole;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RoleChangeRequestForm {

    private boolean  hasUrgency;

    private TokenRole requestRole;

}
