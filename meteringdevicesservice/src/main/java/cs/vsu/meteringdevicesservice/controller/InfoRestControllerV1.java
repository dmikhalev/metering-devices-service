package cs.vsu.meteringdevicesservice.controller;

import cs.vsu.meteringdevicesservice.dto.IdDto;
import cs.vsu.meteringdevicesservice.dto.InfoDto;
import cs.vsu.meteringdevicesservice.entity.Info;
import cs.vsu.meteringdevicesservice.exception.NotFoundException;
import cs.vsu.meteringdevicesservice.service.InfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/")
public class InfoRestControllerV1 {

    private final InfoService infoService;

    @Autowired
    public InfoRestControllerV1(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping(value = "info")
    public ResponseEntity<InfoDto> getInfoById(@RequestBody IdDto id) {
        Info info;
        try {
            info = infoService.findById(id.getId());
        } catch (NotFoundException e) {
            log.error("Info not found", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        InfoDto result = InfoDto.fromInfo(info);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "info/all")
    public ResponseEntity<List<InfoDto>> getAllInfos() {
        List<Info> infos = infoService.findAll();
        List<InfoDto> result = infos.stream().map(InfoDto::fromInfo).collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "info/about")
    public ResponseEntity<InfoDto> getAbout() {
        Info info;
        try {
            info = infoService.getAboutInfo();
        } catch (NotFoundException e) {
            log.error("\"About\" info not found.", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        InfoDto result = InfoDto.fromInfo(info);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "info/faq")
    public ResponseEntity<InfoDto> getFQA() {
        Info info;
        try {
            info = infoService.getFQAInfo();
        } catch (NotFoundException e) {
            log.error("\"FAQ\" info not found.", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        InfoDto result = InfoDto.fromInfo(info);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "info/docs")
    public ResponseEntity<InfoDto> getDocuments() {
        Info info;
        try {
            info = infoService.getDocumentsInfo();
        } catch (NotFoundException e) {
            log.error("\"Documents\" info not found.", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        InfoDto result = InfoDto.fromInfo(info);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "admin/info")
    public void updateInfo(@RequestBody InfoDto infoDto) {
        if (infoDto != null) {
            try {
                Info info = infoService.findByName(infoDto.getName());
                if (info != null) {
                    info.setText(infoDto.getText());
                    infoService.createOrUpdate(info);
                }
            } catch (NotFoundException e) {
                log.error("Info not found.", e);
            }
        }
    }

    @DeleteMapping(value = "admin/info")
    public void deleteInfo(@RequestBody IdDto id) {
        infoService.delete(id.getId());
    }
}
