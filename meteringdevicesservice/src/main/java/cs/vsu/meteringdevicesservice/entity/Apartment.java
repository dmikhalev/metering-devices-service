package cs.vsu.meteringdevicesservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "apartment")
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "water_code", nullable = false)
    private Long waterCode;

    @Column(name = "gas_code", nullable = false)
    private Long gasCode;

    @Column(name = "electricity_code", nullable = false)
    private Long electricityCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    @OneToMany(mappedBy = "apartment")
    private List<Receipt> receipts;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Apartment() {
    }

    public Apartment(Long id, Integer number, Long waterCode, Long gasCode, Long electricityCode) {
        this.id = id;
        this.number = number;
        this.waterCode = waterCode;
        this.gasCode = gasCode;
        this.electricityCode = electricityCode;
    }
}
