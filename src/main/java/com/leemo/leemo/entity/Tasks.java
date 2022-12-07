package com.leemo.leemo.entity;

import com.leemo.leemo.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Tasks")
public class Tasks {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long customerId;
    @Column
    private String headerTitle;

    @Column
    private String title;

    @Enumerated(EnumType.STRING)
    @Column
    private TaskStatus status;

    @Column
    private String requirements;

    @Column
    private String stackTech;

    @Column
    private String developerRequirements;

    @Column
    private Date createdDate;

    @Column
    private Long executorId;

    @Column
    private BigDecimal price;

    @Column
    private Boolean guarantee;

    @Column
    BigDecimal warranty;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Users users;

    @Column
    private Date deadLine;

    public Tasks(Long id,
                 Long customerId,
                 String headerTitle,
                 String title,
                 TaskStatus status,
                 String requirements,
                 String stackTech,
                 String developerRequirements,
                 Date createdDate,
                 Boolean guarantee,
                 BigDecimal price) {
        this.id = id;
        this.customerId = customerId;
        this.headerTitle = headerTitle;
        this.title = title;
        this.status = status;
        this.requirements = requirements;
        this.stackTech = stackTech;
        this.developerRequirements = developerRequirements;
        this.createdDate = createdDate;
        this.guarantee = guarantee;
        this.price = price;
    }
}
