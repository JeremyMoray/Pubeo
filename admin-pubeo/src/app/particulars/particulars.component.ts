import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-particulars',
  templateUrl: './particulars.component.html',
  styleUrls: ['./particulars.component.css']
})
export class ParticularsComponent implements OnInit {

  public newPartEvent: Event;
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
    this.newPartEvent = event;
  }
}
