package bio.harshana.bo.custom;

import bio.harshana.dto.ReservationDTO;
import bio.harshana.projection.UnpaidDetails;
import bio.harshana.bo.SuperBO;

import java.util.List;

public interface ReservationBO extends SuperBO<ReservationDTO, String> {
    String generateID();

    List<UnpaidDetails> getUnpaidDetails();
}
