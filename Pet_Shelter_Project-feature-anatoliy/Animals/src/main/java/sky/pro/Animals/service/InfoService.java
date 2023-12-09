package sky.pro.Animals.service;

import sky.pro.Animals.entity.Info;

import java.util.Collection;

public interface InfoService {
    void checkInfo();

    String editInfo(Long id, String editedText);

    Collection<Info> getAllInfo();

    String getInfoTextById(Long id);
}
