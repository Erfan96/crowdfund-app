package ir.digixo.crowdfundapp.entity;

import ir.digixo.crowdfundapp.entity.enumeration.ProjectStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static ir.digixo.crowdfundapp.entity.Project.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {
    public static final String TABLE_NAME = "project";

    public static final String USER_ID = "user_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    private String description;

    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private BigDecimal targetAmount;

    private BigDecimal collectedAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = USER_ID)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus status;

    @PrePersist
    protected void onCreate() {
        if (startDate == null) {
            startDate = LocalDateTime.now();
        }
    }
}
