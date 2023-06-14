package zti.weatherapi.weather.historical;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zti.weatherapi.db.HistoricalRepository;
import zti.weatherapi.db.model.Historical;
import zti.weatherapi.db.model.OpenMeteoData;
import zti.weatherapi.openmeteo.OpenMeteoService;

/** Historical weather service. */
@Service
public class HistoricalService {

  /**
   * Get historical weather for given location.
   *
   * @param latitude  location latitude
   * @param longitude location longitude
   * @param startDate date from
   * @param endDate   date to
   * @return historical weather
   */
  public OpenMeteoData[] getWeather(
      double latitude, double longitude, String startDate, String endDate) {
    return this.getHistoricalWeather(latitude, longitude, startDate, endDate)
        .toArray(OpenMeteoData[]::new);
  }

  /**
   * Get historical temperature for given location.
   *
   * @param latitude  location latitude
   * @param longitude location longitude
   * @param startDate date from
   * @param endDate   date to
   * @return historical temperature
   */
  public ShortHistoricalModel[] getTemperature(
      double latitude, double longitude, String startDate, String endDate) {
    List<OpenMeteoData> data = this.getHistoricalWeather(latitude, longitude, startDate, endDate);
    ArrayList<ShortHistoricalModel> ret = new ArrayList<>();
    for (OpenMeteoData d : data) {
      ret.add(new ShortHistoricalModel(
          d.getLatitude(), d.getLongitude(), d.getTime(), d.getTemperature()));
    }
    return ret.toArray(ShortHistoricalModel[]::new);
  }

  /**
   * Get historical humidity for given location.
   *
   * @param latitude  location latitude
   * @param longitude location longitude
   * @param startDate date from
   * @param endDate   date to
   * @return historical humidity
   */
  public ShortHistoricalModel[] getHumidity(
      double latitude, double longitude, String startDate, String endDate) {
    List<OpenMeteoData> data = this.getHistoricalWeather(latitude, longitude, startDate, endDate);
    ArrayList<ShortHistoricalModel> ret = new ArrayList<>();
    for (OpenMeteoData d : data) {
      ret.add(new ShortHistoricalModel(
          d.getLatitude(), d.getLongitude(), d.getTime(), d.getHumidity()));
    }
    return ret.toArray(ShortHistoricalModel[]::new);
  }

  /**
   * Get historical pressure for given location.
   *
   * @param latitude  location latitude
   * @param longitude location longitude
   * @param startDate date from
   * @param endDate   date to
   * @return historical pressure
   */
  public ShortHistoricalModel[] getPressure(
      double latitude, double longitude, String startDate, String endDate) {
    List<OpenMeteoData> data = this.getHistoricalWeather(latitude, longitude, startDate, endDate);
    ArrayList<ShortHistoricalModel> ret = new ArrayList<>();
    for (OpenMeteoData d : data) {
      ret.add(new ShortHistoricalModel(
          d.getLatitude(), d.getLongitude(), d.getTime(), d.getPressure()));
    }
    return ret.toArray(ShortHistoricalModel[]::new);
  }

  /**
   * Get historical precipitation for given location.
   *
   * @param latitude  location latitude
   * @param longitude location longitude
   * @param startDate date from
   * @param endDate   date to
   * @return historical precipitation
   */
  public ShortHistoricalModel[] getPrecipitation(
      double latitude, double longitude, String startDate, String endDate) {
    List<OpenMeteoData> data = this.getHistoricalWeather(latitude, longitude, startDate, endDate);
    ArrayList<ShortHistoricalModel> ret = new ArrayList<>();
    for (OpenMeteoData d : data) {
      ret.add(new ShortHistoricalModel(
          d.getLatitude(), d.getLongitude(), d.getTime(), d.getPrecipitation()));
    }
    return ret.toArray(ShortHistoricalModel[]::new);
  }

  /**
   * Get historical cloudcover for given location.
   *
   * @param latitude  location latitude
   * @param longitude location longitude
   * @param startDate date from
   * @param endDate   date to
   * @return historical cloudcover
   */
  public ShortHistoricalModel[] getCloudcover(
      double latitude, double longitude, String startDate, String endDate) {
    List<OpenMeteoData> data = this.getHistoricalWeather(latitude, longitude, startDate, endDate);
    ArrayList<ShortHistoricalModel> ret = new ArrayList<>();
    for (OpenMeteoData d : data) {
      ret.add(new ShortHistoricalModel(
          d.getLatitude(), d.getLongitude(), d.getTime(), d.getCloudcover()));
    }
    return ret.toArray(ShortHistoricalModel[]::new);
  }

  /**
   * Get historical windspeed for given location.
   *
   * @param latitude  location latitude
   * @param longitude location longitude
   * @param startDate date from
   * @param endDate   date to
   * @return historical windspeed
   */
  public ShortHistoricalModel[] getWindspeed(
      double latitude, double longitude, String startDate, String endDate) {
    List<OpenMeteoData> data = this.getHistoricalWeather(latitude, longitude, startDate, endDate);
    ArrayList<ShortHistoricalModel> ret = new ArrayList<>();
    for (OpenMeteoData d : data) {
      ret.add(new ShortHistoricalModel(
          d.getLatitude(), d.getLongitude(), d.getTime(), d.getWindspeed()));
    }
    return ret.toArray(ShortHistoricalModel[]::new);
  }

  private List<OpenMeteoData> getHistoricalWeather(
      double latitude, double longitude, String startDate, String endDate) {
    List<OpenMeteoData> ret;
    // Check if entry already exists
    if (checkIfHistoricalEntryExists(latitude, longitude, startDate, endDate)) {
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
    return ret;
  }

  private boolean checkIfHistoricalEntryExists(
      double latitude, double longitude, String startDate, String endDate) {
    return Boolean.parseBoolean(
        historicalRepository.checkIfExists((int) latitude, (int) longitude, startDate, endDate));
  }

  @Autowired
  private OpenMeteoService openMeteoService;
  @Autowired
  private HistoricalRepository historicalRepository;
}
