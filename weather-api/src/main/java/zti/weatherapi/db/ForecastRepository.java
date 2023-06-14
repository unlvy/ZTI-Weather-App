package zti.weatherapi.db;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import zti.weatherapi.db.model.Forecast;

/** Forecast queries. */
public interface ForecastRepository extends CrudRepository<Forecast, Long> {
  
  @Query(nativeQuery = true, value = "SELECT * FROM forecast WHERE latitude=?1 AND longitude=?2")
  List<Forecast> findByLatitudeAndLongitude(int latitude, int longitude);

  @Query(nativeQuery = true, value = "SELECT check_update_forecast(:latitude, :longitude)")
  String checkForecastUpdate(int latitude, int longitude);

  @Query(nativeQuery = true, value = "SELECT pre_update_forecast(:latitude, :longitude)")
  void preForecastUpdate(int latitude, int longitude);  
}