package kz.frosys.kapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kz.frosys.kapp.model.hrr.Department;
import kz.frosys.kapp.model.hrr.Position;
import kz.frosys.kapp.model.hrr.Profile;
import kz.frosys.kapp.model.hrr.Reason;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileDto {
    private Department department;
    private Position position;
    private Reason reason;
    private UserDto userInstead;
    private UserDto executor;

    public static ProfileDto fromProfile(Profile profile) {
        ProfileDto result = new ProfileDto();

        result.setDepartment(profile.getDepartment());
        result.setExecutor(profile.getExecutor()!=null ? UserDto.fromUser(profile.getExecutor()): null);
        result.setPosition(profile.getPosition());
        result.setReason(profile.getReason());
        result.setUserInstead(profile.getInsteadUser()!=null ? UserDto.fromUser(profile.getInsteadUser()) : null);

        return result;
    }

    public Profile toProfile() {
        Profile profile = new Profile();

        profile.setDepartment(this.department);
        profile.setExecutor(this.executor != null ? this.executor.toUser() : null);
        profile.setPosition(this.position);
        profile.setReason(this.reason);
        profile.setInsteadUser(this.userInstead != null ? this.userInstead.toUser() : null);

        return profile;
    }
}
