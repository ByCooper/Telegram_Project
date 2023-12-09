package sky.pro.Animals.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "info")
public class Info {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String key;
    private String text;

    public Info() {
    }

    public Info(Long id, String key, String text) {
        this.id = id;
        this.key = key;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Info info = (Info) o;
        return Objects.equals(key, info.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public String toString() {
        return "Info{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
