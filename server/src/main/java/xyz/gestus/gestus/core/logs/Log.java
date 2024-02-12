package xyz.gestus.gestus.core.logs;

import jakarta.persistence.*;
import lombok.Data;
import xyz.gestus.gestus.core.user.User;

import java.util.Date;

@Data
@Entity
@Table(name = "logs")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
