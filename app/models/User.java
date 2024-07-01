package models;


import io.ebean.Finder;
import io.ebean.Model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.security.crypto.bcrypt.BCrypt;
import play.data.validation.Constraints;

import java.util.UUID;

@Entity
@Table(name = "users")
public class User extends Model {
    public Integer id;

    @Constraints.Required(message = "username required")
    private String username;

    @Constraints.Required(message = "email required")
    @Column(unique = true)
    private String email;

    @Constraints.Required(message = "password required")
    private String password;

    @Column(nullable = true)
    private String resetToken;

    @Column(nullable = true)
    private Long resetTokenExpiry;

    public Long getResetTokenExpiry() {
        return resetTokenExpiry;
    }

    public void setResetTokenExpiry(Long resetTokenExpiry) {
        this.resetTokenExpiry = resetTokenExpiry;
    }

    // Constructors
    public User() {}

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = hashPassword(password) ;
    }

    public static final Finder<Integer, User> find = new Finder<>(User.class);

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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
        this.password = hashPassword(password);
    }

    // Getter and setter for resetToken
    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public void generateResetToken() {
        this.resetToken = UUID.randomUUID().toString();
        this.resetTokenExpiry = System.currentTimeMillis() + 3600000; // 1 hour expiry
    }

    public boolean isResetTokenValid() {
        return resetToken != null && resetTokenExpiry > System.currentTimeMillis();
    }

    public void clearResetToken() {
        this.resetToken = null;
        this.resetTokenExpiry = null;
    }

    // Hash the password using BCrypt
    private String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }
    // Check if the provided password matches the hashed password
    public boolean checkPassword(String plainTextPassword) {
        return BCrypt.checkpw(plainTextPassword, this.password);
    }
}

