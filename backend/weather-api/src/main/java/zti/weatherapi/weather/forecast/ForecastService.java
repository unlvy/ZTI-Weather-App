package zti.weatherapi.weather.forecast;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zti.weatherapi.db.ForecastRepository;
import zti.weatherapi.db.model.Forecast;
import zti.weatherapi.db.model.OpenMeteoData;
import zti.weatherapi.openmeteo.OpenMeteoService;

/** Weather forecast service. */
@Service
public class ForecastService {

  /**
   * Get 7 day weather forecast for given location.
   *
   * @param latitude  location latitude
   * @param longitude location longitude
   * @return 7 day weather forecast
   */
  public OpenMeteoData[] getWeather(double latitude, double longitude) {
    return this.getWeatherForecast(latitude, longitude).toArray(OpenMeteoData[]::new);
  }

  /**
   * Get 7 day temperature forecast for given location.
   *
   * @param latitude  location latitude
   * @param longitude location longitude
   * @return 7 day temperature forecast
   */
  public ShortForecastModel[] getTemperature(double latitude, double longitude) {
    List<OpenMeteoData> data = this.getWeatherForecast(latitude, longitude);
    ArrayList<ShortForecastModel> ret = new ArrayList<>();
    for (OpenMeteoData d : data) {
      ret.add(new ShortForecastModel(
          d.getLatitude(), d.getLongitude(), d.getTime(), d.getTemperature()));
    }
    return ret.toArray(ShortForecastModel[]::new);
  }

  /**
   * Get 7 day humidity forecast for given location.
   *
   * @param latitude  location latitude
   * @param longitude location longitude
   * @return 7 day humidity forecast
   */
  public ShortForecastModel[] getHumidity(double latitude, double longitude) {
    List<OpenMeteoData> data = this.getWeatherForecast(latitude, longitude);
    ArrayList<ShortForecastModel> ret = new ArrayList<>();
    for (OpenMeteoData d : data) {
      ret.add(new ShortForecastModel(
          d.getLatitude(), d.getLongitude(), d.getTime(), d.getHumidity()));
    }
    return ret.toArray(ShortForecastModel[]::new);
  }

  /**
   * Get 7 day pressure forecast for given location.
   *
   * @param latitude  location latitude
   * @param longitude location longitude
   * @return 7 day pressure forecast
   */
  public ShortForecastModel[] getPressure(double latitude, double longitude) {
    List<OpenMeteoData> data = this.getWeatherForecast(latitude, longitude);
    ArrayList<ShortForecastModel> ret = new ArrayList<>();
    for (OpenMeteoData d : data) {
      ret.add(new ShortForecastModel(
          d.getLatitude(), d.getLongitude(), d.getTime(), d.getPressure()));
    }
    return ret.toArray(ShortForecastModel[]::new);
  }

  /**
   * Get 7 day precipitation forecast for given location.
   *
   * @param latitude  location latitude
   * @param longitude location longitude
   * @return 7 day precipitation forecast
   */
  public ShortForecastModel[] getPrecipitation(double latitude, double longitude) {
    List<OpenMeteoData> data = this.getWeatherForecast(latitude, longitude);
    ArrayList<ShortForecastModel> ret = new ArrayList<>();
    for (OpenMeteoData d : data) {
      ret.add(new ShortForecastModel(
          d.getLatitude(), d.getLongitude(), d.getTime(), d.getPrecipitation()));
    }
    return ret.toArray(ShortForecastModel[]::new);
  }

  /**
   * Get 7 day cloudcover forecast for given location.
   *
   * @param latitude  location latitude
   * @param longitude location longitude
   * @return 7 day cloudcover forecast
   */
  public ShortForecastModel[] getCloudcover(double latitude, double longitude) {
    List<OpenMeteoData> data = this.getWeatherForecast(latitude, longitude);
    ArrayList<ShortForecastModel> ret = new ArrayList<>();
    for (OpenMeteoData d : data) {
      ret.add(new ShortForecastModel(
          d.getLatitude(), d.getLongitude(), d.getTime(), d.getCloudcover()));
    }
    return ret.toArray(ShortForecastModel[]::new);
  }

  /**
   * Get 7 day windspeed forecast for given location.
   *
   * @param latitude  location latitude
   * @param longitude location longitude
   * @return 7 day windspeed forecast
   */
  public ShortForecastModel[] getWindspeed(double latitude, double longitude) {
    List<OpenMeteoData> data = this.getWeatherForecast(latitude, longitude);
    ArrayList<ShortForecastModel> ret = new ArrayList<>();
    for (OpenMeteoData d : data) {
      ret.add(new ShortForecastModel(
          d.getLatitude(), d.getLongitude(), d.getTime(), d.getWindspeed()));
    }
    return ret.toArray(ShortForecastModel[]::new);
  }

  private List<OpenMeteoData> getWeatherForecast(double latitude, double longitude) {
    List<OpenMeteoData> ret;
    List<Forecast> records;
    // Check if forecast exists and is up to date
    if (this.checkIfForecastUpdate(latitude, longitude)) {
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
    return ret;
  }

  private boolean checkIfForecastUpdate(double latitude, double longitude) {
    return Boolean.parseBoolean(forecastRepository.checkForecastUpdate(
        (int) latitude, (int) longitude));
  }

  @Autowired
  private OpenMeteoService openMeteoService;
  @Autowired
  private ForecastRepository forecastRepository;

}

