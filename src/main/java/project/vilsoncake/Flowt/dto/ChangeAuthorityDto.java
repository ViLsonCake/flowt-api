package project.vilsoncake.Flowt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.vilsoncake.Flowt.entity.enumerated.Role;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeAuthorityDto {
    private String username;
    private String email;
    private Set<Role> roles;
    private Boolean active;
}
