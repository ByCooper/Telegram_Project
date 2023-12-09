package sky.pro.Animals.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sky.pro.Animals.entity.Volunteer;
import sky.pro.Animals.service.InfoServiceImpl;
import sky.pro.Animals.service.VolunteerServiceImpl;

import java.sql.Date;
import java.util.Collection;

/**
 * Controller for CRUD operations with volunteers data
 * <p>
 * <hr>
 * <p>
 * Контроллер для CRUD операций с данными волонтеров
 */
@RestController
@RequestMapping(path = "/volunteer")
public class VolunteerController {
    private final VolunteerServiceImpl volunteerService;
    private final InfoServiceImpl infoService;

    public VolunteerController(VolunteerServiceImpl volunteerService, InfoServiceImpl infoService) {
        this.volunteerService = volunteerService;
        this.infoService = infoService;
    }

    @GetMapping(path = "/get")
    public ResponseEntity<Collection<Volunteer>> getAllVolunteers() {
        infoService.checkInfo();
        Collection<Volunteer> volunteers = volunteerService.getAll();
        if (volunteers == null) {
            return ResponseEntity.status(400).build();
        } else {
            return ResponseEntity.ok().body(volunteers);
        }
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<Volunteer> getVolunteer(@PathVariable Long id) {
        infoService.checkInfo();
        Volunteer volunteer = volunteerService.getById(id);
        if (volunteer == null) {
            return ResponseEntity.status(400).build();
        } else {
            return ResponseEntity.ok().body(volunteer);
        }
    }

    @PostMapping(path = "/write")
    public ResponseEntity<Volunteer> writeVolunteer(@RequestParam Long id,
                                                    @RequestParam String fio,
                                                    @RequestParam String address,
                                                    @RequestParam Date birthday,
                                                    @RequestParam String passport,
                                                    @RequestParam Integer chatId,
                                                    @RequestParam String workPosition) {
        infoService.checkInfo();
        Volunteer volunteer = new Volunteer(id, fio, address, birthday, passport, chatId, workPosition);
        Volunteer savedVolunteer = volunteerService.save(volunteer);
        if (savedVolunteer == null) {
            return ResponseEntity.status(400).build();
        } else {
            return ResponseEntity.ok().body(savedVolunteer);
        }
    }

    @PutMapping(path = "/edit")
    public ResponseEntity<Volunteer> editVolunteer(@RequestParam Long id,
                                                   @RequestParam String fio,
                                                   @RequestParam String address,
                                                   @RequestParam Date birthday,
                                                   @RequestParam String passport,
                                                   @RequestParam Integer chatId,
                                                   @RequestParam String workPosition) {
        infoService.checkInfo();
        Volunteer volunteer = new Volunteer(id, fio, address, birthday, passport, chatId, workPosition);
        Volunteer editedVolunteer = volunteerService.save(volunteer);
        if (editedVolunteer == null) {
            return ResponseEntity.status(400).build();
        } else {
            return ResponseEntity.ok().body(editedVolunteer);
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Volunteer> deleteVolunteer(@PathVariable Long id) {
        infoService.checkInfo();
        Volunteer deletedVolunteer = volunteerService.delete(id);
        if (deletedVolunteer == null) {
            return ResponseEntity.status(400).build();
        } else {
            return ResponseEntity.ok().body(deletedVolunteer);
        }
    }
}
