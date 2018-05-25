package project.ck1.thituluan.dto;

import lombok.*;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormTraLoiDto {
    private int sttCauHoi;
    private String cauHoi;
    private String cauTraLoi;
    private String hanhDong = "default";

}
