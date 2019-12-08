import { BrowserModule, Title } from '@angular/platform-browser';
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
  MatProgressSpinnerModule
} from '@angular/material';
import { FormsModule } from '@angular/forms';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { TopToolbarComponent } from './top-toolbar/top-toolbar.component';
import { ParticularsComponent } from './particulars/particulars.component';
import { ProfessionalsComponent } from './professionals/professionals.component';
import { FormsComponent } from './forms/forms.component';

const appRoutes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
    data: {title: 'Login'}
  },
  {
    path: 'accueil',
    component: HomeComponent,
    data: {title: 'Accueil'}
  },
  {
    path: 'particuliers',
    component: ParticularsComponent,
    data: {title: 'Particuliers'}
  },
  {
    path: 'professionels',
    component: ProfessionalsComponent,
    data: {title: 'Professionnels'}
  },
  {
    path: 'formulaires',
    component: FormsComponent,
    data: {title: 'Formulaires'}
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
    FormsComponent
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
    FormsModule,
    RouterModule.forRoot(
      appRoutes,
    ),
    BrowserAnimationsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
