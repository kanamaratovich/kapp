package kz.frosys.kapp.model.hrr;

import kz.frosys.kapp.model.*;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "profiles")
@Data
public class Profile extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "department",nullable = false)
    private Department department;

    @Enumerated(EnumType.STRING)
    @Column(name = "position",nullable = false)
    private Position position;

    @Enumerated(EnumType.STRING)
    @Column(name = "reason",nullable = false)
    private Reason reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instead_user_id",referencedColumnName = "id")
    private User insteadUser;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor_id",referencedColumnName = "id")
    private User executor;


}
