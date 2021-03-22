package cs.vsu.meteringdevicesservice.service;

import cs.vsu.meteringdevicesservice.entity.BankCard;
import cs.vsu.meteringdevicesservice.exception.NotFoundException;
import cs.vsu.meteringdevicesservice.repository.BankCardRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankCardService {
    private final BankCardRepository bankCardRepository;

    public BankCardService(BankCardRepository bankCardRepository) {
        this.bankCardRepository = bankCardRepository;
    }

    public BankCard findById(Long id) {
        return bankCardRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public BankCard findByUserId(Long userId) {
        return bankCardRepository.findByUser_Id(userId).orElseThrow(NotFoundException::new);
    }

    public List<BankCard> findAllByUserId(Long userId) {
        return new ArrayList<>(bankCardRepository.findAllByUser_Id(userId));
    }

    public BankCard createOrUpdate(BankCard bankCard) {
        return bankCardRepository.save(bankCard);
    }

    public void delete(Long id) {
        bankCardRepository.deleteById(id);
    }
}
