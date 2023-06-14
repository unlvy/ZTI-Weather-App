package zti.weatherapi.db.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/** Historical weather data entity. */
@Entity
@Table(name = "historical")
public class Historical extends OpenMeteoData {

  public Historical() {}

  public Historical(OpenMeteoData omd) {
    super(omd);
  }
}
