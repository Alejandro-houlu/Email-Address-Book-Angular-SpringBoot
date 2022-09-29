import { Component, ViewChild } from '@angular/core';
import { MatDrawer } from '@angular/material/sidenav';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'emailAddressBook_frontend';
  
  @ViewChild(MatDrawer)
  drawer!: MatDrawer

  constructor() {}

  ngOnInit(): void {
  }

  toggleSidebar() {
    this.drawer.toggle()
  }
}
