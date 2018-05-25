package project.ck1.thituluan.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name="TraLoi")
@Table(name="traloi")
@IdClass(TraLoiIdClass.class)
public class TraLoi{
    @Id
    @Column(name="Username")
    private String username;
    @Id
    @Column(name="STTCauHoi")
    private int sttCauHoi;
    @Column(name="CauTraLoi")
    private String cauTraLoi;
}
