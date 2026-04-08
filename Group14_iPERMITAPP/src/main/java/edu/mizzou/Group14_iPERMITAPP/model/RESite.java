package edu.mizzou.Group14_iPERMITAPP.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class RESite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String siteAddress;
    private String siteContactPerson;

    @ManyToOne
    @JoinColumn(name = "re_id")
    private RE re;
}