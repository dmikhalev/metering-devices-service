package cs.vsu.meteringdevicesservice.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "receipt_data")
public class ReceiptData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "tariff_id", nullable = false)
    private Tariff tariff;

    @ManyToOne()
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    @ManyToOne()
    @JoinColumn(name = "executor_id", nullable = false)
    private Executor executor;

    public ReceiptData() {

    }

    public ReceiptData(Long id, Tariff tariff, Building building, Executor executor) {
        this.id = id;
        this.tariff = tariff;
        this.building = building;
        this.executor = executor;
    }
}
