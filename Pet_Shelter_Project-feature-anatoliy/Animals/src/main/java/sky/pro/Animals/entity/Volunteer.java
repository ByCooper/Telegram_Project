package sky.pro.Animals.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

import static javax.persistence.GenerationType.*;

@Entity
@Table(name = "employee")
public class Volunteer {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String fio;
    private String address;
    private Date birthday;
    private String passport;
    private Integer chatId;
    private String workPosition;

    public Volunteer() {

    }

    public Volunteer(long id, String johnDoe, String address, java.util.Date date, String ab123456, int chatId, String workPosition) {
    }

    public Volunteer(Long id, String fio, String address, Date birthday, String passport, Integer chatId, String workPosition) {
        this.id = id;
        this.fio = fio;
        this.address = address;
        this.birthday = birthday;
        this.passport = passport;
        this.chatId = chatId;
        this.workPosition = workPosition;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public String getWorkPosition() {
        return workPosition;
    }

    public void setWorkPosition(String workPosition) {
        this.workPosition = workPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteer volunteer = (Volunteer) o;
        return Objects.equals(id, volunteer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "id=" + id +
                ", fio='" + fio + '\'' +
                ", address='" + address + '\'' +
                ", birthday=" + birthday +
                ", passport='" + passport + '\'' +
                ", chatId=" + chatId +
                ", workPosition='" + workPosition + '\'' +
                '}';
    }
}
