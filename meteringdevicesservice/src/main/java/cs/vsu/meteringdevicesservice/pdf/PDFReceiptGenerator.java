package cs.vsu.meteringdevicesservice.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import cs.vsu.meteringdevicesservice.entity.Executor;
import cs.vsu.meteringdevicesservice.entity.Payment;
import cs.vsu.meteringdevicesservice.entity.Tariff;
import cs.vsu.meteringdevicesservice.service.ServiceService;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Slf4j
public class PDFReceiptGenerator {

    public static byte[] generate(Payment payment) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();
            createPDF(document, payment);
            document.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            log.error("Failed to create PDF.");
        }
        return null;
    }

    private static void createPDF(Document document, Payment payment) throws DocumentException {
        document.setMargins(50f, 50f, 50f, 50f);

        Font font = FontFactory.getFont("fonts/Sans_Serif.ttf", "cp1251", BaseFont.EMBEDDED, 18);
        Paragraph paragraph = new Paragraph("Чек №" + payment.getId(), font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        font.setSize(12);
        paragraph = new Paragraph(" ", font);
        document.add(paragraph);
        font.setSize(15);
        paragraph = new Paragraph("Услуги В Дом", font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        font.setSize(12);
        paragraph = new Paragraph(" ", font);
        document.add(paragraph);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");

        String space = "                            ";

        PdfPTable table = new PdfPTable(2);
        PdfPCell cell = new PdfPCell(new Paragraph(space + "Дата оплаты:", font));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(dateFormat.format(payment.getDate()), font));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        Tariff tariff = payment.getReceipt().getReceiptData().getTariff();
        cell = new PdfPCell(new Paragraph(space + "Сервис:", font));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(Objects.requireNonNull(ServiceService.ServiceName.findByName(tariff.getService().getName())).getViewName(), font));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(space + "Тариф:", font));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(String.valueOf(tariff.getCost()), font));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(space + "Сумма:", font));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(payment.getSum() + "руб.", font));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(" ", font));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(" ", font));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        Executor executor = payment.getReceipt().getReceiptData().getExecutor();
        cell = new PdfPCell(new Paragraph(space + "Исполнитель:", font));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(executor.getName(), font));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(space + "ИНН:", font));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(String.valueOf(executor.getTaxId()), font));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(space + "Адресс:", font));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(executor.getAddress(), font));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(space + "Тел.", font));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(executor.getPhoneNumber(), font));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        document.add(table);
    }
}
