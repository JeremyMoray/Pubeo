import { Component, OnInit } from '@angular/core';
import { PubeoService } from '../shared/pubeo.service';
import { Sticker } from '../shared/sticker.model';

@Component({
  selector: 'app-forms',
  templateUrl: './forms.component.html',
  styleUrls: ['./forms.component.css']
})
export class FormsComponent implements OnInit {

  list: Sticker[];
  constructor(private pubeoService: PubeoService) { }

  ngOnInit() {
    return this.pubeoService.getAllStickers().subscribe(data => this.list = data);
  }

}
