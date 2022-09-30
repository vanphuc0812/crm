package cybersoft.java18.crm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserModel {
    private int id;
    private String email;
    private String fullName;
    private String password;
    private String avatar;
    private int roleId;

    public UserModel(String email, String fullName, String password, int roleId) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.roleId = roleId;
    }
}
