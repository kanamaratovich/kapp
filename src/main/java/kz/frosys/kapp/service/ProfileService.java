package kz.frosys.kapp.service;

import kz.frosys.kapp.model.hrr.Profile;

import java.util.List;

public interface ProfileService {
    Profile create(Profile newProfile);
    List<Profile> getAll();
    Profile findByUsername(String name);
    Profile findById(Long id);
    Profile update(Profile profile);


    void delete(Long id);
}
