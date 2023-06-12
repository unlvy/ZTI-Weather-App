package zti.weatherapi.db;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import zti.weatherapi.db.model.Historical;

/** Historical queries. */
public interface HistoricalRepository extends CrudRepository<Historical, Long> {

  @Query(nativeQuery = true, value = "SELECT check_historical_record(:latitude, :longitude, :startDate) AND check_historical_record(:latitude, :longitude, :endDate)")
  String checkIfExists(
      @Param("latitude") int latitude,
      @Param("longitude") int longitude,
      @Param("startDate") String startDate,
      @Param("endDate") String endDate);

  @Query(nativeQuery = true, value = "SELECT * FROM historical WHERE latitude=?1 AND longitude=?2 AND TO_DATE(time,'YYYY-MM-DD') >= TO_DATE(?3,'YYYY-MM-DD') AND TO_DATE(time,'YYYY-MM-DD') <= TO_DATE(?4,'YYYY-MM-DD')")
  List<Historical> getHistoricalFromRange(
      int latitude,
      int longitude,
      String startDate,
      String endDate);
}