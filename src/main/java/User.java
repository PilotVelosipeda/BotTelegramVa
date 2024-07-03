import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "user_name")
    private String userName;
    private int countBallov;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCountBallov() {
        return countBallov;
    }

    public void setCountBallov(int countBallov) {
        this.countBallov = countBallov;
    }
    public void addBall() {
        countBallov += 1;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", userName='" + userName + '\'' +
                ", countBallov=" + countBallov +
                '}';
    }
}
