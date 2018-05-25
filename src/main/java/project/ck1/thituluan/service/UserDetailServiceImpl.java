package project.ck1.thituluan.service;

import project.ck1.thituluan.entity.TaiKhoan;
import project.ck1.thituluan.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    TaiKhoanRepository taiKhoanRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<TaiKhoan> taiKhoans = taiKhoanRepository.findAll();
        TaiKhoan taiKhoan = new TaiKhoan();
        //find by username
        for(TaiKhoan tk : taiKhoans){
            if(tk.getUsername().equals(username) == true){
                taiKhoan = tk;
            }
        }

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(taiKhoan.getCapbac());
        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        grantedAuthoritySet.add(grantedAuthority);
        return new org.springframework.security.core.userdetails.User(taiKhoan.getUsername(),taiKhoan.getPassword(),grantedAuthoritySet);
    }
}
