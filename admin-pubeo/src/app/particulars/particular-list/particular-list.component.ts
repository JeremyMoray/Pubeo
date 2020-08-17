import { Component, OnInit } from '@angular/core';
import { ParticularsService } from 'src/app/shared/particulars.service';
import { Particulars } from 'src/app/shared/particulars.model';

@Component({
  selector: 'app-particular-list',
  templateUrl: './particular-list.component.html',
  styleUrls: ['./particular-list.component.css']
})
export class ParticularListComponent implements OnInit {

  list: Particulars[];
  constructor(private particularsService: ParticularsService) { }

  ngOnInit() {
    this.list = this.particularsService.getAllParticulars();
  }

}
