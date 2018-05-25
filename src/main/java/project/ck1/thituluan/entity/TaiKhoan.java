package project.ck1.thituluan.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="TaiKhoan")
@Table(name="taikhoan")
@Getter
@Setter
public class TaiKhoan {
    @Id
    @Column(name="Username")
    private String username;
    @Column(name="Password")
    private String password;
    @Column(name="CapBac")
    private String capbac;

}
