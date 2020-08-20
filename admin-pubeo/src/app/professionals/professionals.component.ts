import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-professionals',
  templateUrl: './professionals.component.html',
  styleUrls: ['./professionals.component.css']
})
export class ProfessionalsComponent implements OnInit {

  public newProEvent: Event;
  toggleForm: boolean;
  constructor() { }

  ngOnInit() {
    this.toggleForm = true;
  }

  showForm(){
    this.toggleForm = !this.toggleForm;
  }

  eventCloseComponent(event: Event){
    this.toggleForm = true;
    this.newProEvent = event;
  }
}
