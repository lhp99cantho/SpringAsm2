package com.example.asg2.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Member implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    protected Integer memberId;

//    @NotNull
    @Column(name = "first_name")
    protected String firstName;

//    @NotNull
    @Column(name = "last_name")
    protected String lastName;

    @NotNull
    @Column(name = "username", nullable = false)
    protected String username;

    @NotNull
    @Column(name = "password", nullable = false)
    protected String password;

    protected String phone;

    @Email
    protected String email;

    protected String description;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected List<Content> listOfContent;

    @CreationTimestamp
    protected Date createdDate;

    @UpdateTimestamp
    protected Date updatedDate;
}
