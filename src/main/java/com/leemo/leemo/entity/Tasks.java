package com.leemo.leemo.entity;

import com.leemo.leemo.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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

        @Column(name = "header")
        private String header;

        @Column(name = "title")
        private String title;

        @Enumerated(EnumType.STRING)
        @Column(name = "status")
        private TaskStatus status;

        @Column(name = "requirements")
        private String requirements;

          @Column(name = "stack_tech")
          private String stackTech;

        @Column(name = "developer_requirements")
        private String developerRequirements;

        @Column
        private Date createdDate;
}
