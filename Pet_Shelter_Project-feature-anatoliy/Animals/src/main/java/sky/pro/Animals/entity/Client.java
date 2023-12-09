package sky.pro.Animals.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String address;
    private Date birthday;
    private String passport;
    private Long chatId;
    @OneToMany(mappedBy = "client")
    private Collection<Pet> pets;

    public Client() {
    }

    public Client(Long id, String firstName, String lastName,
                  String userName, String address,
                  Date birthday, String passport, Long chatId, Collection<Pet> pets) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;

        this.address = address;
        this.birthday = birthday;
        this.passport = passport;
        this.chatId = chatId;
        this.pets = pets;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Collection<Pet> getPets() {
        return pets;
    }

    public void setPets(Collection<Pet> pets) {
        this.pets = pets;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +

                ", address='" + address + '\'' +
                ", birthday=" + birthday +
                ", passport='" + passport + '\'' +
                ", chatId=" + chatId +
                ", pets=" + pets +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) && Objects.equals(firstName, client.firstName) && Objects.equals(lastName, client.lastName) && Objects.equals(userName, client.userName) && Objects.equals(address, client.address) && Objects.equals(birthday, client.birthday) && Objects.equals(passport, client.passport) && Objects.equals(chatId, client.chatId) && Objects.equals(pets, client.pets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, userName, address, birthday, passport, chatId, pets);
    }
}
