package co.com.cava.examples.service.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDTO {

	@NotNull(message = "this element is not null")
	@NotEmpty(message = "this elemet is not empty")
	@NotBlank(message = "this camp is not blank")
	private String userName;
	@NotNull(message = "this element is not null")
	@NotEmpty(message = "this elemet is not empty")
	@NotBlank(message = "this camp is not blank")
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
