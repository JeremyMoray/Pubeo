import { Component, OnInit } from '@angular/core';
import { PubeoService } from 'src/app/shared/services/pubeo.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-particular',
  templateUrl: './particular.component.html',
  styleUrls: ['./particular.component.css']
})
export class ParticularComponent implements OnInit {

  constructor(private pubeoService : PubeoService) { }

  ngOnInit() {
  }
}
