import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { environment } from 'src/environment';
import { WeatherData, ShortWeatherData } from '../weather-data/i-weather-data';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ForecastService {

  constructor(private http: HttpClient) { }

  public getTemperature(latitude: number, longitude: number): Observable<ShortWeatherData[]> {
    return this.http.get<ShortWeatherData[]>(`${environment.forecastTemperature}${this.getQuery(latitude, longitude)}`);
  }
  public getHumidity(latitude: number, longitude: number): Observable<ShortWeatherData[]> {
    return this.http.get<ShortWeatherData[]>(`${environment.forecastHumidity}${this.getQuery(latitude, longitude)}`);
  }

  public getPressure(latitude: number, longitude: number): Observable<ShortWeatherData[]> {
    return this.http.get<ShortWeatherData[]>(`${environment.forecastPressure}${this.getQuery(latitude, longitude)}`);
  }

  public getPrecipitation(latitude: number, longitude: number): Observable<ShortWeatherData[]> {
    return this.http.get<ShortWeatherData[]>(`${environment.forecastPrecipitation}${this.getQuery(latitude, longitude)}`);
  }

  public getCloudcover(latitude: number, longitude: number): Observable<ShortWeatherData[]> {
    return this.http.get<ShortWeatherData[]>(`${environment.forecastCloudcover}${this.getQuery(latitude, longitude)}`);
  }

  public getWindspeed(latitude: number, longitude: number): Observable<ShortWeatherData[]> { 
    return this.http.get<ShortWeatherData[]>(`${environment.forecastWindspeed}${this.getQuery(latitude, longitude)}`);
  }
  public getAll(latitude: number, longitude: number): Observable<WeatherData[]> { 
    return this.http.get<WeatherData[]>(`${environment.forecastAll}${this.getQuery(latitude, longitude)}`);
  }

  private getQuery(latitude: number, longitude: number): string {
    return `?latitude=${latitude}&longitude=${longitude}`;
  }
}
