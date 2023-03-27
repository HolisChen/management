package com.github.hollis.dao.entity;

import com.github.hollis.dao.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "t_user", catalog = "management")
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class UserEntity extends BaseEntity {
    @Column(name = "login_id")
    private String loginId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    private Byte status;

    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

}
