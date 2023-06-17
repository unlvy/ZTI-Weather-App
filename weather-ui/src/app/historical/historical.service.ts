import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { environment } from 'src/environment';
import { WeatherData, ShortWeatherData } from '../weather-data/i-weather-data';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HistoricalService {

  constructor(private http: HttpClient) { }

  public getTemperature(latitude: number, longitude: number, startDate: string, endDate: string): Observable<ShortWeatherData[]> {
    return this.http.get<ShortWeatherData[]>(`${environment.historicalTemperature}${this.getQuery(latitude, longitude, startDate, endDate)}`);
  }
  public getHumidity(latitude: number, longitude: number, startDate: string, endDate: string): Observable<ShortWeatherData[]> {
    return this.http.get<ShortWeatherData[]>(`${environment.historicalHumidity}${this.getQuery(latitude, longitude, startDate, endDate)}`);
  }

  public getPressure(latitude: number, longitude: number, startDate: string, endDate: string): Observable<ShortWeatherData[]> {
    return this.http.get<ShortWeatherData[]>(`${environment.historicalPressure}${this.getQuery(latitude, longitude, startDate, endDate)}`);
  }

  public getPrecipitation(latitude: number, longitude: number, startDate: string, endDate: string): Observable<ShortWeatherData[]> {
    return this.http.get<ShortWeatherData[]>(`${environment.historicalPrecipitation}${this.getQuery(latitude, longitude, startDate, endDate)}`);
  }

  public getCloudcover(latitude: number, longitude: number, startDate: string, endDate: string): Observable<ShortWeatherData[]> {
    return this.http.get<ShortWeatherData[]>(`${environment.historicalCloudcover}${this.getQuery(latitude, longitude, startDate, endDate)}`);
  }

  public getWindspeed(latitude: number, longitude: number, startDate: string, endDate: string): Observable<ShortWeatherData[]> {
    return this.http.get<ShortWeatherData[]>(`${environment.historicalWindspeed}${this.getQuery(latitude, longitude, startDate, endDate)}`);
  }
  public getAll(latitude: number, longitude: number, startDate: string, endDate: string): Observable<WeatherData[]> {
    console.log('CALLING', `${environment.historicalAll}${this.getQuery(latitude, longitude, startDate, endDate)}`);
    return this.http.get<WeatherData[]>(`${environment.historicalAll}${this.getQuery(latitude, longitude, startDate, endDate)}`);
  }

  private getQuery(latitude: number, longitude: number, startDate: string, endDate: string): string {
    return `?latitude=${latitude}&longitude=${longitude}&startDate=${startDate}&endDate=${endDate}`;
  }
}
