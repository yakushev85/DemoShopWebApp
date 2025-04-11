import { Component, OnInit } from '@angular/core';
import { User, UserService } from '../../core';

@Component({
  standalone: false,
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  users: User[] = [];
  isAdmin = false;
  pageNumber = 1;
  pageSize = 10;
  totalElements = 0;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.listValues();

    this.userService.isAdmin.subscribe(
      (value) => {
        this.isAdmin = value;
      }
    );
  }

  listValues() {
    this.userService.getAll(this.pageNumber - 1, this.pageSize).subscribe(
      (value) => {
        if (value) {
          this.users = (value.content as User[]);

          this.pageNumber = value.pageable.pageNumber + 1;
          this.pageSize = value.pageable.pageSize;
          this.totalElements = value.totalElements;
        }
      }
    );
  }

  updatePageSize(value: string) {
    this.pageSize = +value;
    this.listValues();
  }
}
