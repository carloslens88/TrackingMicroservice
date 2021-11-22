package com.deere.isg.trackingmicroservice.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author Carlos Lens
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="t_session")
public class Session implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @NotNull
    private UUID userId;
    @NotNull
    private UUID machineId;
    @NotNull
    private long orgId;
    @NotNull
    private Timestamp startAt;
    private Timestamp endAt;
}