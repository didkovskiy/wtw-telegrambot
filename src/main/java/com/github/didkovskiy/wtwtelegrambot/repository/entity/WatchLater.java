package com.github.didkovskiy.wtwtelegrambot.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Data
@Entity
@Table(name = "wtw_list")
public class WatchLater {
    @Id
    @Column(name = "id", nullable = false, length = 20)
    private String id;

    private String title;
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "wtw_list_x_user",
            joinColumns = @JoinColumn(name = "wtw_list_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<TelegramUser> users;

    public void addUser(TelegramUser user) {
        if (isNull(users))
            users = new ArrayList<>();
        users.add(user);
    }
}
