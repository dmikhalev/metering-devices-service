package cs.vsu.meteringdevicesservice.controller;


import cs.vsu.meteringdevicesservice.dto.IdDto;
import cs.vsu.meteringdevicesservice.dto.ReceiptDto;
import cs.vsu.meteringdevicesservice.entity.Receipt;
import cs.vsu.meteringdevicesservice.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/receipt")
public class ReceiptRestControllerV1 {

    private final ReceiptService receiptService;

    @Autowired
    public ReceiptRestControllerV1(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @GetMapping()
    public ResponseEntity<ReceiptDto> getReceiptById(@RequestBody IdDto id) {
        Receipt receipt = receiptService.findById(id.getId());
        if (receipt == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        ReceiptDto result = ReceiptDto.fromReceipt(receipt);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping()
    public void createOrUpdateReceipt(@RequestBody ReceiptDto receiptDto) {
        if (receiptDto != null) {
            Receipt receipt = receiptDto.toReceipt();
            receiptService.createOrUpdate(receipt);
        }
    }

    @DeleteMapping()
    public void deleteReceipt(@RequestBody IdDto id) {
        receiptService.delete(id.getId());
    }
}
