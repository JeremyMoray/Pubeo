import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RouterModule, Routes } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { 
  MatButtonModule, 
  MatCardModule, 
  MatDialogModule, 
  MatInputModule, 
  MatTableModule,
  MatToolbarModule, 
  MatMenuModule,
  MatIconModule, 
  MatProgressSpinnerModule,
  MatBadgeModule,
  MatDatepickerModule
} from '@angular/material';
import { MatMomentDateModule, MAT_MOMENT_DATE_ADAPTER_OPTIONS } from "@angular/material-moment-adapter";
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { TopToolbarComponent } from './top-toolbar/top-toolbar.component';
import { ParticularsComponent } from './particulars/particulars.component';
import { ProfessionalsComponent } from './professionals/professionals.component';
import { StickersComponent } from './stickers/stickers.component';
import { ParticularComponent } from './particulars/particular/particular.component';
import { ParticularListComponent } from './particulars/particular-list/particular-list.component';
import { ParticularDetailsComponent } from './particulars/particular-details/particular-details.component';
import { ProfessionalComponent } from './professionals/professional/professional.component';
import { ProfessionalListComponent } from './professionals/professional-list/professional-list.component';
import { ProfessionalDetailsComponent } from './professionals/professional-details/professional-details.component';
import { PubeoService } from './shared/services/pubeo.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { CustomHttpInterceptor } from './custom-http-interceptor';
import { ConnectionErrorComponent } from './shared/errors/connection-error/connection-error.component';
import { AuthGuardService } from './shared/services/auth-guard.service';

const appRoutes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
    data: {title: 'Login'}
  },
  {
    path: 'accueil',
    component: HomeComponent,
    data: {title: 'Accueil'},
    canActivate: [AuthGuardService]
  },
  {
    path: 'particuliers',
    component: ParticularsComponent,
    data: {title: 'Particuliers'},
    canActivate: [AuthGuardService]
  },
  {
    path: 'particuliers/details/:Id',
    component: ParticularDetailsComponent,
    data: {title: 'Details particuliers'},
    canActivate: [AuthGuardService]
  },
  {
    path: 'professionels',
    component: ProfessionalsComponent,
    data: {title: 'Professionnels'},
    canActivate: [AuthGuardService]
  },
  {
    path: 'professionels/details/:Id',
    component: ProfessionalDetailsComponent,
    data: {title: 'Details professionels'},
    canActivate: [AuthGuardService]
  },
  {
    path: 'stickers',
    component: StickersComponent,
    data: {title: 'Stickers'},
    canActivate: [AuthGuardService]
  },
  {
    path: 'connectionError',
    component: ConnectionErrorComponent,
    data: {title: 'Connection error'}
  },
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    TopToolbarComponent,
    ParticularsComponent,
    ProfessionalsComponent,
    StickersComponent,
    ParticularComponent,
    ParticularListComponent,
    ParticularDetailsComponent,
    ProfessionalComponent,
    ProfessionalListComponent,
    ProfessionalDetailsComponent,
    ConnectionErrorComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatButtonModule, 
    MatCardModule, 
    MatDialogModule, 
    MatInputModule, 
    MatTableModule,
    MatToolbarModule, 
    MatMenuModule,
    MatIconModule, 
    MatProgressSpinnerModule,
    MatBadgeModule,
    MatDatepickerModule,
    MatMomentDateModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot(
      appRoutes,
    ),
    BrowserAnimationsModule,
    HttpClientModule
  ],
  providers: [
    PubeoService, 
    {
      provide: HTTP_INTERCEPTORS,
      useClass: CustomHttpInterceptor,
      multi: true
    },
    {provide: MAT_MOMENT_DATE_ADAPTER_OPTIONS, 
      useValue: {
        useUtc: true
      }
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
