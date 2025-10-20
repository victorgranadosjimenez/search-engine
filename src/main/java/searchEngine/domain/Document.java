package searchEngine.domain;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 2000)
    private String content;

    @Transient // No se guarda en la base de datos
    public String getText() {
        return (title + " " + content).toLowerCase();
    }
}
