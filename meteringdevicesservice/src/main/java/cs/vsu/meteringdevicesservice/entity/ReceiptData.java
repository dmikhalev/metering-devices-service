package cs.vsu.meteringdevicesservice.entity;

import lombok.Data;

import javax.persistence.*;

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

    public ReceiptData(Tariff tariff, Building building, Executor executor) {
        this.tariff = tariff;
        this.building = building;
        this.executor = executor;
    }
}
