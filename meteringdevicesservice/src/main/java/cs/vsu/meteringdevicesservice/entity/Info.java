package cs.vsu.meteringdevicesservice.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "info")
public class Info {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "text", nullable = false)
    private String text;

    public Info() {

    }

    public Info(Long id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
    }
}
