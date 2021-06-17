package cs.vsu.meteringdevicesservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "building")
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "postcode", nullable = false)
    private Long postcode;

    @OneToMany(mappedBy = "building")
    private List<Apartment> apartments;

    public Building() {

    }

    public Building(Long id, String number, String street, String city, Long postcode) {
        this.id = id;
        this.number = number;
        this.street = street;
        this.city = city;
        this.postcode = postcode;
    }
}
