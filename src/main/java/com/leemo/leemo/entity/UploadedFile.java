package com.leemo.leemo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "file")
public class UploadedFile {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String fileId;

    @Column
    private String fileName;

    @Column
    private String fileType;

    @Type(type = "org.hibernate.type.BinaryType")
    @Lob
    @Column
    @Basic(fetch = FetchType.LAZY)
    private byte[] fileData;

}
