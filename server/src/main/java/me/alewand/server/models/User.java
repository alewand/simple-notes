package me.alewand.server.models;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.alewand.server.types.others.UserRole;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    public User(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    @Id
    @GeneratedValue
    @JsonIgnore
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID userId;

    @NotBlank(message = "Nick nie może być pusty.")
    @Size(min = 3, max = 20, message = "Nick musi mieć od 3 do 20 znaków.")
    @Pattern(regexp = "^\\S*$", message = "Nick nie może zawierać spacji.")
    @Pattern(regexp = "^\\w+$", message = "Nick nie może zawierać znaków specjalnych.")
    @Column(unique = true, nullable = false)
    private String nickname;

    @NotBlank(message = "Email nie może być pusty.")
    @Size(max = 255, message = "Email nie może przekraczać 255 znaków.")
    @Pattern(regexp = "^\\S*$", message = "Email nie może zawierać spacji.")
    @Email(message = "Email ma nieprawidłowy format.")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Hasło nie może być puste.")
    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.USER;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Session> sessions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Note> notes;

    @Override
    @JsonIgnore
    public String getUsername() {
        return nickname;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

}
