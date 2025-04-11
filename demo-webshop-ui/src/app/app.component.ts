import { Component } from '@angular/core';
import { UserService } from './core';

@Component({
  standalone: false,
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private userService: UserService) {}

  ngOnInit() {
    this.userService.populate();
  }
}
