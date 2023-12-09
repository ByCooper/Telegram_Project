package sky.pro.Animals.entity;

import sky.pro.Animals.model.PetVariety;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private Date birthday;
    private boolean alive;
    private PetVariety petVariety;
    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;


    public Pet(Long id, String name, Date birthday, boolean alive, PetVariety petVariety, Client client) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.alive = alive;
        this.petVariety = petVariety;
        this.client = client;
    }

    public Pet() {

    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public PetVariety getPetVariety() {
        return petVariety;
    }

    public void setPetVariety(PetVariety petVariety) {
        this.petVariety = petVariety;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return  name + '\n' +
                "Дата рождения = " + birthday +  '\n';
    }
}
