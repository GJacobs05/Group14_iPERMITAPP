package edu.mizzou.Group14_iPERMITAPP.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Data
public class Payment {

    @Id
    private String paymentID;

    private Date paymentDate;
    private String paymentMethod;
    private int last4DigitsofCard;
    private String cardHolderName;
    private boolean paymentApproved;

    @OneToOne
    @JoinColumn(name = "permit_request_id")
    private PermitRequest permitRequest;
}