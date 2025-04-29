package ir.digixo.crowdfundapp.entity;

import ir.digixo.crowdfundapp.entity.enumeration.ContributionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static ir.digixo.crowdfundapp.entity.Contribution.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contribution {
    public static final String TABLE_NAME = "contribution";
    public static final String USER_ID = "user_id";
    public static final String PROJECT_ID = "project_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = USER_ID, nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = PROJECT_ID, nullable = false)
    private Project project;

    @Column(nullable = false)
    private BigDecimal amount;

    private LocalDateTime contributionDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContributionStatus status;

    @PrePersist
    protected void onCreate() {
        if (contributionDate == null) {
            contributionDate = LocalDateTime.now();
        }
    }

}
