package org.kdt.mooluck.domain.admin.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class AgencyStaffDTO {
    private Integer staff_id;
    private String agency_name;
    private String staff_name;
    private String staff_number;
    private String staff_email;
    private int contact_period;
    private String password;


    public void validate() {
        if (staff_email == null || staff_email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email은 필수 입력값입니다.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password는 필수 입력값입니다.");
        }
    }
}
