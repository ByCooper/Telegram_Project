package sky.pro.Animals.service;

import sky.pro.Animals.entity.Volunteer;

import java.util.Collection;

public interface VolunteerService {
    Collection<Volunteer> getAll();

    Volunteer getById(Long id);

    Volunteer save(Volunteer volunteer);

    Volunteer delete(Long id);
}
