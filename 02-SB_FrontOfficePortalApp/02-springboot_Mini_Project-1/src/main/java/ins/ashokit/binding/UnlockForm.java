package ins.ashokit.binding;

import lombok.Data;

@Data
public class UnlockForm {
     
	private String email;
	private String temppdw;
	private String newpdw;
	private String confirmpdw;
}
