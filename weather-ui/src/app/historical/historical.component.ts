import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { HistoricalService } from './historical.service';
import { ShortWeatherData, WeatherData } from '../weather-data/i-weather-data';

enum HistoricalType {
  Temperature = 1,
  Humidity = 2,
  Pressure = 3,
  Precipitation = 4,
  Cloudcover = 5,
  Windspeed = 6,
  All = 7
}

@Component({
  selector: 'app-historical',
  templateUrl: './historical.component.html',
  styleUrls: ['./historical.component.css']
})
export class HistoricalComponent {

  public minDate: Date;
  public maxDate: Date;
  public inProgress = false;
  public shortValueType = '';
  public shortDataColumns = ['time', 'value']
  public shortHistoricalVisible = false;
  public shortHistorical: ShortWeatherData[] = [];
  public dataColumns = ['time', 'temperature', 'humidity', 'pressure', 'precipitation', 'cloudcover', 'windspeed'];
  public historicalVisible = false;
  public historical: WeatherData[] = [];

  public historicalTypes = [
    HistoricalType.Temperature,
    HistoricalType.Humidity,
    HistoricalType.Pressure,
    HistoricalType.Precipitation,
    HistoricalType.Cloudcover,
    HistoricalType.Windspeed,
    HistoricalType.All
  ];

  public historicalForm: FormGroup = new FormGroup({
    latitude: new FormControl('', { validators: [Validators.required, Validators.min(-89), Validators.max(89), Validators.pattern('-?[0-9]+')] }),
    longitude: new FormControl('', { validators: [Validators.required, Validators.min(-179), Validators.max(179), Validators.pattern('-?[0-9]+')] }),
    historicalType: new FormControl('', { validators: [Validators.required] }),
    startDate: new FormControl('', { validators: [Validators.required] }),
    endDate: new FormControl('', { validators: [Validators.required] })
  });

  constructor(private historicalService: HistoricalService) {
    this.minDate = new Date(2010, 0, 1);
    this.maxDate = new Date(Date.now() - 12096e5); // two weeks ago
  }

  public get historicalLatitude() { return this.historicalForm.get('latitude'); }
  public get hisotricalLongitude() { return this.historicalForm.get('longitude'); }
  public get historicalType() { return this.historicalForm.get('historicalType'); }
  public get historicalStartDate() { return this.historicalForm.get('startDate'); }
  public get historicalEndDate() { return this.historicalForm.get('endDate'); }

  public getHistoricalTypeLabel(type: HistoricalType) {
    switch (type) {
      case HistoricalType.Temperature:
        return "Temperature";
      case HistoricalType.Humidity:
        return "Humidity";
      case HistoricalType.Pressure:
        return "Pressure";
      case HistoricalType.Precipitation:
        return "Precipitation";
      case HistoricalType.Cloudcover:
        return "Cloudcover";
      case HistoricalType.Windspeed:
        return "Windspeed";
      case HistoricalType.All:
        return "All";
      default:
        return "Unknown type";
    }
  }

  public onHistoricalSubmit(): void {
    this.inProgress = true;
    const startDate = this.historicalForm.value.startDate;
    const endDate = this.historicalForm.value.endDate;
    if (this.checkDates(startDate, endDate)) {
      endDate.setDate(endDate.getDate() + 1);
    }
    const startDateString = this.getDateString(startDate);
    const endDateString = this.getDateString(endDate);
    const type = this.historicalForm.value.historicalType;
    const latitude = this.historicalForm.value.latitude;
    const longitude = this.historicalForm.value.longitude;
    switch (type) {
      case HistoricalType.Temperature:
        this.historicalService.getTemperature(latitude, longitude, startDateString, endDateString)
          .subscribe(data => this.processShortData(data, 'Temperature [°C]'));
        break;
      case HistoricalType.Humidity:
        this.historicalService.getHumidity(latitude, longitude, startDateString, endDateString)
          .subscribe(data => this.processShortData(data, 'Humidity [%]'));
        break;
      case HistoricalType.Pressure:
        this.historicalService.getPressure(latitude, longitude, startDateString, endDateString)
          .subscribe(data => this.processShortData(data, 'Pressure [hPa]'));
        break;
      case HistoricalType.Precipitation:
        this.historicalService.getPrecipitation(latitude, longitude, startDateString, endDateString)
          .subscribe(data => this.processShortData(data, 'Precipitation [mm]'));
        break;
      case HistoricalType.Cloudcover:
        this.historicalService.getCloudcover(latitude, longitude, startDateString, endDateString)
          .subscribe(data => this.processShortData(data, 'Cloudcover [%]'));
        break;
      case HistoricalType.Windspeed:
        this.historicalService.getWindspeed(latitude, longitude, startDateString, endDateString)
          .subscribe(data => this.processShortData(data, 'Windspeed [m/s]'));
        break;
      case HistoricalType.All:
        this.historicalService.getAll(latitude, longitude, startDateString, endDateString)
          .subscribe(data => this.processData(data));
        break;
      default:
        break;
    }
  }

  public resetMaxDate() {
    if (this.historicalForm.value.endDate) {
      this.maxDate = new Date(Date.now() - 12096e5);
    }
  }

  public setMaxDate() {
    const newMaxDate = new Date(this.historicalForm.value.startDate);
    newMaxDate.setDate(newMaxDate.getDate() + 30);
    this.maxDate = new Date(newMaxDate);
  }


  private processData(data: WeatherData[]): void {
    this.historical = data;
    this.inProgress = false;
    this.shortHistoricalVisible = false;
    this.historicalVisible = true;
  }

  private processShortData(data: ShortWeatherData[], type: string): void {
    this.shortHistorical = data;
    this.shortValueType = type;
    this.inProgress = false;
    this.shortHistoricalVisible = true;
    this.historicalVisible = false;
  }

  private checkDates(startDate: Date, endDate: Date) {
    return startDate.getTime() === endDate.getTime();
  }

  private getDateString(date: Date): string {
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toLocaleString('en-US', { minimumIntegerDigits: 2 });
    const day = date.getDate().toLocaleString('en-US', { minimumIntegerDigits: 2 });
    return `${year}-${month}-${day}`;
  }

}