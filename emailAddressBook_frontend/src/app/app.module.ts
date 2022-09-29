import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { AddComponent } from './Components/AddComponent/add.component';
import { ListComponent } from './Components/ListComponent/list.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule } from './material.module'; 
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { LoggerModule, NgxLoggerLevel } from 'ngx-logger';
import { ContactService } from './Services/ContactService';
import { ContactDetailsComponent } from './Components/ContactDetailsComponent/contact-details.component';

const appRoutes: Routes = [
  {path: 'add', component: AddComponent},
  {path: 'list', component: ListComponent},
  {path: 'contactDetails/:userId', component: ContactDetailsComponent},
  {path:"**", redirectTo:'/', pathMatch:'full'}
]

@NgModule({
  declarations: [
    AppComponent,
    AddComponent,
    ListComponent,
    ContactDetailsComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MaterialModule,
    ReactiveFormsModule,
    HttpClientModule,
    LoggerModule.forRoot({
    serverLoggingUrl: '/api/logs',
    level: NgxLoggerLevel.DEBUG,
    serverLogLevel: NgxLoggerLevel.ERROR}),
    RouterModule.forRoot(appRoutes,{useHash: true})
  ],
  providers: [ContactService],
  bootstrap: [AppComponent]
})
export class AppModule { }
