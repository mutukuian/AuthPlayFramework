package models;


import io.ebean.Finder;
import io.ebean.Model;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import play.data.validation.Constraints;

@Entity
@Table(name = "users")
public class Login extends Model {
    @Constraints.Required(message = "email required")
    private String email;

    @Constraints.Required(message = "password required")
    private String password;

    // Constructors
    public Login() {}

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static final Finder<Integer, Login> find = new Finder<>(Login.class);
    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
