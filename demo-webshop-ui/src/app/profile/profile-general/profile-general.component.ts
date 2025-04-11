import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User, UserService } from '../../core';

@Component({
  standalone: false,
  selector: 'app-profile-general',
  templateUrl: './profile-general.component.html',
  styleUrls: ['./profile-general.component.css']
})
export class ProfileGeneralComponent implements OnInit {
  isSubmitting: boolean;
  resetPasswordForm: FormGroup;
  currentUser: User | undefined;
  error: string = "";

  constructor(
    private router: Router,
    private userService: UserService,
    private fb: FormBuilder
    ) {
      this.isSubmitting = false;

      this.resetPasswordForm = this.fb.group({
        'currentPassword': ['', Validators.required],
        'newPassword': ['', Validators.required],
        'reNewPassword': ['', Validators.required]
      });

      this.userService.currentUser.subscribe(
        (userData) => {
          this.currentUser = userData;
        }
      );
     }

  ngOnInit(): void {
  }

  submitForm() {
    this.isSubmitting = true;

    this.userService.resetPassword(this.resetPasswordForm.value)
    .subscribe({
      next: (data) => {
        this.router.navigateByUrl('/');
      },
      error: (err) => {
        this.error = "Wrong current password.";
        console.log(err);
      },
      complete: () => {
        this.isSubmitting = false;
      }
    });
  }

}
