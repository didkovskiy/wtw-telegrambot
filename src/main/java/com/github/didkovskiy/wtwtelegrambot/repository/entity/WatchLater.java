package com.github.didkovskiy.wtwtelegrambot.repository.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WatchLater that = (WatchLater) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 891516853;
    }
}
