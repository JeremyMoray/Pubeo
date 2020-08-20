import { Component, OnInit } from '@angular/core';
import { PubeoService } from '../shared/services/pubeo.service';
import { Sticker } from '../shared/models/sticker.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-stickers',
  templateUrl: './stickers.component.html',
  styleUrls: ['./stickers.component.css']
})
export class StickersComponent implements OnInit {

  stickers: Sticker[];
  constructor(private pubeoService: PubeoService, private router : Router) { }

  ngOnInit() {
    this.pubeoService.getAllStickers()
        .subscribe(data => this.stickers = data, () => this.connectionError());
  }

  connectionError(){
    this.router.navigate(['connectionError']);
  }
}
