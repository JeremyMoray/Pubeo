import { Component, OnInit } from '@angular/core';
import { PubeoService } from '../shared/services/pubeo.service';
import { Sticker } from '../shared/models/sticker.model';

@Component({
  selector: 'app-forms',
  templateUrl: './forms.component.html',
  styleUrls: ['./forms.component.css']
})
export class FormsComponent implements OnInit {

  list: Sticker[];
  constructor(private particularsService: PubeoService) { }

  ngOnInit() {
    this.list = this.particularsService.getAllStickers();
  }

}
