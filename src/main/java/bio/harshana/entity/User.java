package bio.harshana.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import bio.harshana.dto.UserDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class User {
    @Id
    @Column(nullable = false)
    private int id;
    private String username;
    private String password;

    public UserDTO toUserDTO() {
        return new UserDTO(this.id, this.username, this.password);
    }
}
