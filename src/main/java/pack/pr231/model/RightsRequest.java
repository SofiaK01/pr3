package pack.pr231.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rights")
public class RightsRequest {

    @Id
    @Column(name = "user_id")
    private int user_id;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;


}
