package project.vilsoncake.Flowt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePasswordDto {
    private String newPassword;
    private Integer code;
}
