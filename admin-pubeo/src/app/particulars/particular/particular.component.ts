import { Component, OnInit } from '@angular/core';
import { ParticularsService } from 'src/app/shared/particulars.service';

@Component({
  selector: 'app-particular',
  templateUrl: './particular.component.html',
  styleUrls: ['./particular.component.css']
})
export class ParticularComponent implements OnInit {

  constructor(private particularsService : ParticularsService) { }

  ngOnInit() {
  }

}
