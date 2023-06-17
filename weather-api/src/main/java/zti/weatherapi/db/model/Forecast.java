package zti.weatherapi.db.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/** Weather forecast data entity. */
@Entity
@Table(name = "forecast")
public class Forecast extends OpenMeteoData {

  public Forecast() {}

  public Forecast(OpenMeteoData omd) {
    super(omd);
  }
}
