package project.ck1.thituluan.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="CauHoi")
@Table(name="cauhoi")
@Getter
@Setter
public class CauHoi {

    @Id
    @Column(name="STTCauHoi")
    private int sttCauHoi;
    @Column(name="NoiDungCauHoi")
    private String noiDungCauHoi;
    @Column(name="CauTraLoiChinhXac")
    private String cauTraLoiChinhXac;
}
