package it.fabio.libreria.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Getter
    private int id;

    @Column(nullable = false)
    private String image;

    public Image(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}