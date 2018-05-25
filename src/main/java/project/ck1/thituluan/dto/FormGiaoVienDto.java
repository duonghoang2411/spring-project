package project.ck1.thituluan.dto;

import lombok.*;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormGiaoVienDto {
    private int sttCauHoi;
    private String cauHoi;
    private String cauTraLoiSV;
    private String cauTraLoiChinhXac;
}
