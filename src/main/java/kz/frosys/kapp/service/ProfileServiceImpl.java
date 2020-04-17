package kz.frosys.kapp.service;

import kz.frosys.kapp.model.Role;
import kz.frosys.kapp.model.Status;
import kz.frosys.kapp.model.User;
import kz.frosys.kapp.model.hrr.Profile;
import kz.frosys.kapp.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Profile create(Profile newProfile) {
        //executor null
        newProfile.setStatus(Status.ACTIVE);

        Profile createdProfile = profileRepository.save(newProfile);
        log.info("IN create - profile:{} successfully created",createdProfile);

        return createdProfile;
    }

    @Override
    public List<Profile> getAll() {
        return null;
    }

    @Override
    public Profile findByUsername(String name) {
        return null;
    }

    @Override
    public Profile findById(Long id) {
        return null;
    }

    @Override
    public Profile update(Profile profile) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
