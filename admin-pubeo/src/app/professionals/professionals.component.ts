import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-professionals',
  templateUrl: './professionals.component.html',
  styleUrls: ['./professionals.component.css']
})
export class ProfessionalsComponent implements OnInit {

  toggleForm: boolean;
  constructor() { }

  ngOnInit() {
    this.toggleForm = true;
  }

  showForm(){
    this.toggleForm = !this.toggleForm;
  }
}
