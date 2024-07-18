package me.khw7385.trip_planner.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.khw7385.trip_planner.base.BaseEntity;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User extends BaseEntity {
    @Column(name = "user_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Builder
    public User(String loginId, String password, String name) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
    }
}
