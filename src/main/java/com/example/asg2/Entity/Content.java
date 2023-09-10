package com.example.asg2.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DynamicUpdate
public class Content {
    @Id
    @Column(name = "content_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer contentId;

    @NotNull
    @Column(name = "title", nullable = false)
    protected String title;

    @NotNull
    @Column(name= "brief", nullable = false)
    protected String brief;

    @NotNull
    @Column(name = "content", nullable = false)
    protected String content;

    protected Integer sort;

    @CreationTimestamp
    protected Date createdDate;

    @UpdateTimestamp
    protected Date updatedDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    protected Member member;
}
