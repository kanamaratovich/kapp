package kz.frosys.kapp.dto.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kz.frosys.kapp.model.Status;
import kz.frosys.kapp.model.User;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Status status;

    public User toUser(){
        User user = new User();
        user.setId(this.id);
        user.setUsername(this.username);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setEmail(this.email);
        user.setStatus(this.status);

        return user;
    }

    public static AdminDto fromUser(User user){
        AdminDto userDto = new AdminDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setStatus(user.getStatus());

        return userDto;

    }

}