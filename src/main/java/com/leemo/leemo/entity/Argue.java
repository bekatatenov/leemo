package com.leemo.leemo.entity;

import com.leemo.leemo.enums.ArgueEnums;
import com.leemo.leemo.enums.Roles;
import lombok.*;

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

    @Enumerated(EnumType.STRING)
    private Roles decisionInFavor;

    @Column
    private Date createdDate;

    @Column
    private Date resolvedDate;
}
