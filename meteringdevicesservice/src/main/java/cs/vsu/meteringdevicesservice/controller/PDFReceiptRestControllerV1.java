package cs.vsu.meteringdevicesservice.controller;

import cs.vsu.meteringdevicesservice.dto.PDFDto;
import cs.vsu.meteringdevicesservice.dto.UserDto;
import cs.vsu.meteringdevicesservice.entity.Receipt;
import cs.vsu.meteringdevicesservice.entity.User;
import cs.vsu.meteringdevicesservice.pdf.PDFReceiptGenerator;
import cs.vsu.meteringdevicesservice.service.ReceiptService;
import cs.vsu.meteringdevicesservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/pdf_receipt")
public class PDFReceiptRestControllerV1 {

    private final UserService userService;
    private final ReceiptService receiptService;

    public PDFReceiptRestControllerV1(UserService userService, ReceiptService receiptService) {
        this.userService = userService;
        this.receiptService = receiptService;
    }

    @PostMapping(value = "/{days_count}")
    public ResponseEntity<PDFDto> getPDFReceipt(@RequestBody UserDto userDto, @PathVariable Integer days_count) {
        User user = userService.findByUsername(userDto.getUsername());
        if (user == null) {
            log.error("User not found.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<Receipt> receipts = receiptService.findPaidReceiptsFotTheLastDays(user.getId(), days_count);
        final List<byte[]> pdfs = new ArrayList<>();
        receipts.forEach(r -> pdfs.add(PDFReceiptGenerator.generate(r.getPayment())));
        PDFDto result = new PDFDto();
        result.setPdfs(pdfs);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
