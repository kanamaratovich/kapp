package kz.frosys.kapp.rest;

import kz.frosys.kapp.dto.UserDto;
import kz.frosys.kapp.model.User;
import kz.frosys.kapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/users/")
public class UserRestControllerV1 {
    private final UserService userService;

    @Autowired
    public UserRestControllerV1(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id){
        User user = userService.findById(id);

        if(user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserDto result = UserDto.fromUser(user);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<UserDto>> getAll(){
        List<User> users = userService.getAll();

        if(users.size()==0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<UserDto> result = users.stream().map(UserDto::fromUser).collect(Collectors.toList());

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    /*

    ADMIN

    @PostMapping(value = "")
    public ResponseEntity<UserDto> create(UserDto userDto){

        User user = userDto.toUser();



        return userDto;
    }*/


}
