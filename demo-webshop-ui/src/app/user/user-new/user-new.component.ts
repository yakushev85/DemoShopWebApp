import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService, User } from '../../core';

@Component({
  standalone: false,
  selector: 'app-user-new',
  templateUrl: './user-new.component.html',
  styleUrls: ['./user-new.component.css']
})
export class UserNewComponent implements OnInit {
  userForm : FormGroup = this.fb.group({
    username: ['', [Validators.required, Validators.minLength(2)]],
    password: ['', [Validators.required, Validators.minLength(3)]],
    repassword: ['', [Validators.required, Validators.minLength(3)]],
    role: ''
  });
  isSubmitting = false;
  isAdmin = false;

  constructor(private router: Router,
    private userService: UserService,
    private fb: FormBuilder) { }

  ngOnInit(): void {
    this.userService.isAdmin.subscribe(
      (value) => {
        this.isAdmin = value;
      }
    );
  }

  submitForm() {
    this.isSubmitting = true;

    let newUser = (this.userForm.value as { username: string, password: string, role: string });

    this.userService.add(newUser)
    .subscribe({
      next: () => {
        this.isSubmitting = false;
        this.router.navigateByUrl('/users');
      },
      error: () => {
        this.isSubmitting = false;
      }
    });
  }
}
