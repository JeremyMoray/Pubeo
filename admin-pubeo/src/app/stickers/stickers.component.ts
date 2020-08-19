import { Component, OnInit } from '@angular/core';
import { PubeoService } from '../shared/services/pubeo.service';
import { Sticker } from '../shared/models/sticker.model';

@Component({
  selector: 'app-stickers',
  templateUrl: './stickers.component.html',
  styleUrls: ['./stickers.component.css']
})
export class StickersComponent implements OnInit {

  stickers: Sticker[];
  constructor(private pubeoService: PubeoService) { }

  ngOnInit() {
    this.pubeoService.getAllStickers()
        .subscribe(data => this.stickers = data);
  }

}
