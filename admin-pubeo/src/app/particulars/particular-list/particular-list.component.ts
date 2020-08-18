import { Component, OnInit } from '@angular/core';
import { PubeoService } from 'src/app/shared/services/pubeo.service';
import { Particulars } from 'src/app/shared/models/particulars.model';

@Component({
  selector: 'app-particular-list',
  templateUrl: './particular-list.component.html',
  styleUrls: ['./particular-list.component.css']
})
export class ParticularListComponent implements OnInit {

  list: Particulars[];
  constructor(private pubeoService: PubeoService) { }

  ngOnInit() {
    this.list = this.pubeoService.getAllParticulars();
  }

}
