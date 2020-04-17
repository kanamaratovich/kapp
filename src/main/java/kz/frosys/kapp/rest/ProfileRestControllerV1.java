package kz.frosys.kapp.rest;

import kz.frosys.kapp.dto.ProfileDto;
import kz.frosys.kapp.model.hrr.Profile;
import kz.frosys.kapp.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/profiles/")
public class ProfileRestControllerV1 {
    private final ProfileService profileService;

    @Autowired
    public ProfileRestControllerV1(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping(value = "")
    public ResponseEntity<ProfileDto> createProfile(@RequestBody ProfileDto newProfile){
        Profile profile = profileService.create(newProfile.toProfile());

        ProfileDto result = ProfileDto.fromProfile(profile);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<ProfileDto> getById(@PathVariable(name = "id") Long id){
        Profile profile = profileService.findById(id);

        ProfileDto result = ProfileDto.fromProfile(profile);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<ProfileDto>> getAllProfiles(){
        List<Profile> profiles = profileService.getAll();

        List<ProfileDto> result = profiles.stream().map(ProfileDto::fromProfile).collect(Collectors.toList());

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<ProfileDto> update(@PathVariable(name = "id") Long id,@RequestBody ProfileDto profile){
        Profile updatedProfile = profileService.update(profile.toProfile());

        ProfileDto result = ProfileDto.fromProfile(updatedProfile);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity delete(@PathVariable(name = "id") Long id){
        profileService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
