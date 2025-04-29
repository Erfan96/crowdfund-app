package ir.digixo.crowdfundapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static ir.digixo.crowdfundapp.entity.User.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    public static final String TABLE_NAME = "User";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    private LocalDateTime startDate;

    @PrePersist
    protected void onCreate() {
        if (startDate == null) {
            startDate = LocalDateTime.now();
        }
    }
}
