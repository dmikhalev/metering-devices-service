package cs.vsu.meteringdevicesservice.service;

import cs.vsu.meteringdevicesservice.entity.Info;
import cs.vsu.meteringdevicesservice.exception.NotFoundException;
import cs.vsu.meteringdevicesservice.repository.InfoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InfoService {
    private static final String ABOUT = "about";
    private static final String FAQ = "faq";
    private static final String DOCUMENTS = "docs";

    private final InfoRepository infoRepository;


    public InfoService(InfoRepository infoRepository) {
        this.infoRepository = infoRepository;
    }

    public Info findById(Long id) throws NotFoundException {
        return infoRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Info getAboutInfo() {
        return infoRepository.findByName(ABOUT).orElseThrow(NotFoundException::new);
    }

    public Info getFQAInfo() {
        return infoRepository.findByName(FAQ).orElseThrow(NotFoundException::new);
    }

    public Info getDocumentsInfo() {
        return infoRepository.findByName(DOCUMENTS).orElseThrow(NotFoundException::new);
    }

    public List<Info> findAll() {
        return new ArrayList<>(infoRepository.findAll());
    }

    public Info findByName(String name) {
        return infoRepository.findByName(name).orElseThrow(NotFoundException::new);
    }

    public Info createOrUpdate(Info info) {
        return infoRepository.save(info);
    }

    public void delete(Long id) {
        infoRepository.deleteById(id);
    }
}
