package bio.harshana.bo.custom;

import bio.harshana.bo.SuperBO;
import bio.harshana.dto.ReservationDTO;

public interface ReservationBO extends SuperBO<ReservationDTO, String> {
    String generateID();
}
