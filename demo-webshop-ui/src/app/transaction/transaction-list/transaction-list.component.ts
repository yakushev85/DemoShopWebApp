import { Component, OnInit } from '@angular/core';
import { Transaction, TransactionService, UserService } from '../../core';

@Component({
  standalone: false,
  selector: 'app-transaction-list',
  templateUrl: './transaction-list.component.html',
  styleUrls: ['./transaction-list.component.css']
})
export class TransactionListComponent implements OnInit {
  transactions: Transaction[] = [];
  isAdmin = false;
  pageNumber = 1;
  pageSize = 10;
  totalElements = 0;

  constructor(
    private transactionService: TransactionService,
    private userService: UserService
    ) { }

  ngOnInit(): void {
    this.listValues();

    this.userService.isAdmin.subscribe(
      (value) => {
        this.isAdmin = value;
      }
    );
  }

  listValues() {
    this.transactionService.getAll(this.pageNumber - 1, this.pageSize).subscribe(
      (value) => {
        if (value) {
          this.transactions = (value.content as Transaction[]);

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
