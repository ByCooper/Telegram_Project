package sky.pro.Animals.entity;

import javax.persistence.*;

import java.util.Arrays;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "avatar")
public class PetAvatar {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String filePath;
    private long fileSize;
    private String mediaType;
    @Lob
    private byte[] data;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    public PetAvatar() {
    }

    public PetAvatar(Long id, String filePath, long fileSize, String mediaType, byte[] data, Pet pet) {
        this.id = id;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.mediaType = mediaType;
        this.data = data;
        this.pet = pet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetAvatar petAvatar = (PetAvatar) o;
        return fileSize == petAvatar.fileSize && Objects.equals(id, petAvatar.id) && Objects.equals(filePath, petAvatar.filePath) && Objects.equals(pet, petAvatar.pet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filePath, fileSize, pet);
    }

    @Override
    public String toString() {
        return "PetAvatar{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", mediaType='" + mediaType + '\'' +
                ", data=" + Arrays.toString(data) +
                ", pet=" + pet +
                '}';
    }
}
