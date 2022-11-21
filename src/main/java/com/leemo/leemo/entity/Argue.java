package com.leemo.leemo.entity;

import com.leemo.leemo.enums.ArgueEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "argue")
public class Argue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Tasks task;

    @Enumerated(EnumType.STRING)
    private ArgueEnums argueEnums;

    @Column
    private Date createdDate;
}
