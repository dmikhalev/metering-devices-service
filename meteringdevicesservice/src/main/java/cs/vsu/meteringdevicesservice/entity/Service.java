package cs.vsu.meteringdevicesservice.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "service")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    public Service() {

    }

    public Service(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
