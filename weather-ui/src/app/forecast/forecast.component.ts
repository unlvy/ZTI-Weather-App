import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ForecastService } from './forecast.service';
import { ShortWeatherData, WeatherData } from '../weather-data/i-weather-data';

enum ForecastType {
  Temperature = 1,
  Humidity = 2,
  Pressure = 3,
  Precipitation = 4,
  Cloudcover = 5,
  Windspeed = 6,
  All = 7
}

@Component({
  selector: 'app-forecast',
  templateUrl: './forecast.component.html',
  styleUrls: ['./forecast.component.css']
})
export class ForecastComponent {

  constructor(private forecastService: ForecastService) { }

  public inProgress = false;
  public shortValueType = '';
  public shortDataColumns = ['time', 'value']
  public shortForecastVisible = false;
  public shortForecast: ShortWeatherData[] = [];
  public dataColumns = ['time', 'temperature', 'humidity', 'pressure', 'precipitation', 'cloudcover', 'windspeed'];
  public forecastVisible = false;
  public forecast: WeatherData[] = [];  

  public forecastTypes = [
    ForecastType.Temperature,
    ForecastType.Humidity,
    ForecastType.Pressure,
    ForecastType.Precipitation,
    ForecastType.Cloudcover,
    ForecastType.Windspeed,
    ForecastType.All
  ];

  public forecastForm: FormGroup = new FormGroup({
    latitude: new FormControl('', { validators: [Validators.required, Validators.min(-89), Validators.max(89), Validators.pattern('-?[0-9]+')] }),
    longitude: new FormControl('', { validators: [Validators.required, Validators.min(-179), Validators.max(179), Validators.pattern('-?[0-9]+')] }),
    forecastType: new FormControl('', { validators: [Validators.required] })
  });

  public get forecastLatitude() { return this.forecastForm.get('latitude'); }
  public get forecastLongitude() { return this.forecastForm.get('longitude'); }
  public get forecastType() { return this.forecastForm.get('forecastType'); }

  public getForecastTypeLabel(type: ForecastType) {
    switch (type) {
      case ForecastType.Temperature:
        return "Temperature";
      case ForecastType.Humidity:
        return "Humidity";
      case ForecastType.Pressure:
        return "Pressure";
      case ForecastType.Precipitation:
        return "Precipitation";
      case ForecastType.Cloudcover:
        return "Cloudcover";
      case ForecastType.Windspeed:
        return "Windspeed";
      case ForecastType.All:
        return "All";
      default:
        return "Unknown type";
    }
  }

  public onForecastSubmit(): void {
    this.inProgress = true;
    const type = this.forecastForm.value.forecastType;
    const latitude = this.forecastForm.value.latitude;
    const longitude = this.forecastForm.value.longitude;
    switch(type) {
      case ForecastType.Temperature:
        this.forecastService.getTemperature(latitude, longitude).subscribe(data => this.processShortData(data, 'Temperature [°C]'));
        break;
      case ForecastType.Humidity:
        this.forecastService.getHumidity(latitude, longitude).subscribe(data => this.processShortData(data, 'Humidity [%]'));
        break;
      case ForecastType.Pressure:
        this.forecastService.getPressure(latitude, longitude).subscribe(data => this.processShortData(data, 'Pressure [hPa]'));
        break;
      case ForecastType.Precipitation:
        this.forecastService.getPrecipitation(latitude, longitude).subscribe(data => this.processShortData(data, 'Precipitation [mm]'));
        break;
      case ForecastType.Cloudcover:
        this.forecastService.getCloudcover(latitude, longitude).subscribe(data => this.processShortData(data, 'Cloudcover [%]'));
        break;
      case ForecastType.Windspeed:
        this.forecastService.getWindspeed(latitude, longitude).subscribe(data => this.processShortData(data, 'Windspeed [m/s]'));
        break;
      case ForecastType.All:
        this.forecastService.getAll(latitude, longitude).subscribe(data => this.processData(data));
        break;
      default:
        break;
    }
  }

  private processData(data: WeatherData[]): void { 
    this.forecast = data;
    this.inProgress = false;
    this.shortForecastVisible = false;
    this.forecastVisible = true;
  }

  private processShortData(data: ShortWeatherData[], type: string): void {
    this.shortForecast = data;
    this.shortValueType = type;
    this.inProgress = false;
    this.shortForecastVisible = true;
    this.forecastVisible = false;
  }

}