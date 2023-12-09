package sky.pro.Animals.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sky.pro.Animals.entity.Info;
import sky.pro.Animals.service.InfoServiceImpl;

import java.util.Collection;

@RestController
@RequestMapping(path = "/info")
public class InfoController {
    private final InfoServiceImpl infoService;

    public InfoController(InfoServiceImpl infoService) {
        this.infoService = infoService;
    }

    @GetMapping()
    public ResponseEntity<Collection<Info>> getAll() {
        Collection<Info> info = infoService.getAllInfo();
        if (info == null) {
            return ResponseEntity.status(400).build();
        } else {
            return ResponseEntity.ok().body(info);
        }
    }

    @PostMapping(path = "/edit")
    public ResponseEntity<String> editInfo(@RequestParam Long id, @RequestParam String editedText) {
        String info = infoService.editInfo(id, editedText);
        if (info == null) {
            return ResponseEntity.status(400).build();
        } else {
            return ResponseEntity.ok().body(info);
        }
    }
}
