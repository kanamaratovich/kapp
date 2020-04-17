package kz.frosys.kapp.rest;

import javassist.NotFoundException;
import kz.frosys.kapp.dto.UserDto;
import kz.frosys.kapp.dto.admin.AdminDto;
import kz.frosys.kapp.model.User;
import kz.frosys.kapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminRestControllerV1 {

    private final UserService userService;

    @Autowired
    public AdminRestControllerV1(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "user/{id}")
    public ResponseEntity<AdminDto> getUserById(@PathVariable(name = "id") Long id){
        User user = userService.findById(id);

        if(user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        AdminDto result = AdminDto.fromUser(user);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping(value = "users")
    public ResponseEntity<List<AdminDto>> getAll(){
        List<User> users = userService.getAll();

        if(users.size()==0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<AdminDto> result = users.stream().map(AdminDto::fromUser).collect(Collectors.toList());

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping(value = "user")
    public ResponseEntity<AdminDto> create(UserDto newUser){

        User user = newUser.toUser();

        user = userService.register(user);

        return new ResponseEntity<>(AdminDto.fromUser(user),HttpStatus.OK);
    }

    @PutMapping(value = "user/{id}")
    public ResponseEntity<AdminDto> update(@PathVariable() Long id,@RequestBody AdminDto userDto){

        userDto.setId(id);

        User user = userDto.toUser();

        user = userService.update(user);

        return new ResponseEntity<>(AdminDto.fromUser(user),HttpStatus.OK);
    }

    @PostMapping(value = "role/{roleId}/user/{userId}/")
    public ResponseEntity addRoleToUser(@PathVariable(name = "roleId") Long roleId,@PathVariable(name = "userId") Long userId){
        try {
            userService.addRoleToUser(roleId,userId);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "user/{userId}")
    public ResponseEntity deleteUser(@PathVariable(name = "userId") Long userId){

        userService.delete(userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "role/{roleId}/user/{userId}")
    public ResponseEntity deleteRoleFromUser(@PathVariable(name = "roleId") Long roleId,@PathVariable(name = "userId") Long userId){
        try {
            userService.deleteRoleFromUser(roleId,userId);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
