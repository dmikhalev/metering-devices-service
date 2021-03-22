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
}
