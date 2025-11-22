package app.pijulcel.demo.Services.Interfaces;

import app.pijulcel.demo.DTOs.LoginPostDTO;
import app.pijulcel.demo.DTOs.LoginResponseDTO;

public interface IAuth {

    LoginResponseDTO authenticate(LoginPostDTO loginDTO);
    
}
