package cs.vsu.meteringdevicesservice.service;

import cs.vsu.meteringdevicesservice.entity.Payment;
import cs.vsu.meteringdevicesservice.exception.NotFoundException;
import cs.vsu.meteringdevicesservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment findById(Long id) throws NotFoundException {
        return paymentRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Payment findLastPaymentByUserId(long id){
        return paymentRepository.findLastByUserId(id).orElseThrow(NotFoundException::new);
    }

    public Payment createOrUpdate(Payment payment) {
        return paymentRepository.save(payment);
    }

    public void delete(Long id) {
        paymentRepository.deleteById(id);
    }

    public Double calcPrice(long prevVal, long currVal, double cost) {
        return (currVal - prevVal) * cost;
    }
}
