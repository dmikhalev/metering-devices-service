package cs.vsu.meteringdevicesservice.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "executor")
public class Executor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "tax_id", nullable = false)
    private Long taxId;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    public Executor() {

    }

    public Executor(Long id, String name, String address, Long taxId, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.taxId = taxId;
        this.phoneNumber = phoneNumber;
    }
}
