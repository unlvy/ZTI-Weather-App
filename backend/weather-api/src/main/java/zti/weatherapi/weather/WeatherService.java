package zti.weatherapi.weather;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zti.weatherapi.db.ForecastRepository;
import zti.weatherapi.db.HistoricalRepository;
import zti.weatherapi.db.model.Forecast;
import zti.weatherapi.db.model.Historical;
import zti.weatherapi.db.model.OpenMeteoData;
import zti.weatherapi.openmeteo.OpenMeteoService;

/** Historical weather and weather forecast service. */
@Service
public class WeatherService {

  /**
   * Get 7 day weather forecast for given location.
   *
   * @param latitude  location latitude
   * @param longitude location longitude
   * @return 7 day weather forecast
   */
  public OpenMeteoData[] getWeatherForecast(double latitude, double longitude) {
    List<OpenMeteoData> ret;
    List<Forecast> records;
    // Check if forecast doesnt exists or is older than 1 hour
    if (Boolean.parseBoolean(forecastRepository.checkForecastUpdate(
        (int) latitude, (int) longitude))) {
      // Read from api and update db
      this.forecastRepository.preForecastUpdate((int) longitude, (int) latitude);
      ret = this.openMeteoService.getWeatherForecast(latitude, longitude);
      records = new ArrayList<>();
      for (OpenMeteoData record : ret) {
        records.add(new Forecast(record));
      }
      this.forecastRepository.saveAll(records);
    } else {
      // Read from db
      records = this.forecastRepository.findByLatitudeAndLongitude((int) latitude, (int) longitude);
      ret = new ArrayList<OpenMeteoData>(records);
    }
    return ret.toArray(OpenMeteoData[]::new);
  }

  /**
   * Get historical weather for given location.
   *
   * @param latitude  location latitude
   * @param longitude location longitude
   * @param startDate date from
   * @param endDate   date to
   * @return historical weather
   */
  public OpenMeteoData[] getHistoricalWeather(
      double latitude, double longitude, String startDate, String endDate) {
    List<OpenMeteoData> ret;
    // Check if entry already exists
    if (Boolean.parseBoolean(
        historicalRepository.checkIfExists((int) latitude, (int) longitude, startDate, endDate))) {
      // Read from db
      List<Historical> h = this.historicalRepository.getHistoricalFromRange(
          (int) latitude, (int) longitude, startDate, endDate);
      ret = new ArrayList<OpenMeteoData>(h);
    } else {
      // Read from OpenMeteo and update db
      ret = this.openMeteoService.getHistoricalWeather(latitude, longitude, startDate, endDate);
      ArrayList<Historical> records = new ArrayList<>();
      for (OpenMeteoData record : ret) {
        records.add(new Historical(record));
      }
      this.historicalRepository.saveAll(records);
    }
    return ret.toArray(OpenMeteoData[]::new);
  }

  @Autowired
  private OpenMeteoService openMeteoService;
  @Autowired
  private ForecastRepository forecastRepository;
  @Autowired
  private HistoricalRepository historicalRepository;
}
