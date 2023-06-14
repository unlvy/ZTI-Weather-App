import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ForecastComponent } from './forecast/forecast.component';
import { HistoricalComponent } from './historical/historical.component';



const routes: Routes = [
  { path: 'forecast', component: ForecastComponent },
  { path: 'historical', component: HistoricalComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
