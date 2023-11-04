import { GlobalService } from './../../shared/service/global.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/shared/service/user.service';
import { UntypedFormBuilder, UntypedFormControl, UntypedFormGroup, Validators } from '@angular/forms';

import { NzFormTooltipIcon } from 'ng-zorro-antd/form';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  validateForm!: UntypedFormGroup;
  validateForm1!: UntypedFormGroup;

  user: any;

  isVisible = false;
  isOkLoading = false;

  name: any;
  password: any;
  password_again: any;
  balance: any;

  showModal(): void {
    this.isVisible = true;
  }

  handleOk(): void {
    this.isOkLoading = true;
    if(this.password !== this.password_again) {
      alert('The passwords are not the same!');
    } else if(this.name === null || this.password === null || this.balance === null) {
      alert('Please fill in all the blanks!');
    }
    else {
      let data = {
        name: this.name,
        passWord: this.password,
        balance: this.balance
      }
      this.userService.addUser$(data).subscribe(
        data => {
          console.log(data);
          // this.getUserData(this.name, this.password);
        }
      );
    }

    setTimeout(() => {
      this.isVisible = false;
      this.isOkLoading = false;
    }, 3000);
  }

  handleCancel(): void {
    this.isVisible = false;
  }

  submitForm(): void {
    this.getUserData(this.validateForm.value.userName, this.validateForm.value.password);
  }

  constructor(private fb: UntypedFormBuilder, private router: Router, private userService: UserService, private globalService: GlobalService) { }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      userName: [null, [Validators.required]],
      password: [null, [Validators.required]],
      remember: [true]
    });

    this.validateForm1 = this.fb.group({
      email: [null, [Validators.email, Validators.required]],
      password: [null, [Validators.required]],
      checkPassword: [null, [Validators.required, this.confirmationValidator]],
      nickname: [null, [Validators.required]],
      phoneNumberPrefix: ['+86'],
      phoneNumber: [null, [Validators.required]],
      website: [null, [Validators.required]],
      captcha: [null, [Validators.required]],
      agree: [false]
    });
  }

  goToHomepage() {
    this.router.navigate(['/dashboard/details']);
  }

  public getUserData(name: string, passwd: string) {
    this.userService.getUserData$(name, passwd).subscribe(
      (data) => {
        console.log('user data from backend', data);
        if (data === null) {
          alert('Wrong username or password!');
          return;
        }
        this.user = data;
        if (this.validateForm.valid) {
          console.log('submit', this.validateForm.value);
          if (this.user.name === name && this.user.passWord === passwd) {
            console.log('login success');
            this.globalService.setUserId(data.id);
            this.globalService.setUserName(data.name);
            this.globalService.setDate('2023-08-22');
            this.goToHomepage();
          }
        } else {
          Object.values(this.validateForm.controls).forEach(control => {
            if (control.invalid) {
              control.markAsDirty();
              control.updateValueAndValidity({ onlySelf: true });
            }
          });
        }
      }
    )
  }

  captchaTooltipIcon: NzFormTooltipIcon = {
    type: 'info-circle',
    theme: 'twotone'
  };

  submitForm1(): void {
    if (this.validateForm1.valid) {
      console.log('submit', this.validateForm1.value);
    } else {
      Object.values(this.validateForm1.controls).forEach(control => {
        if (control.invalid) {
          control.markAsDirty();
          control.updateValueAndValidity({ onlySelf: true });
        }
      });
    }
  }

  updateConfirmValidator1(): void {
    /** wait for refresh value */
    // Promise.resolve().then(() => this.validateForm1.controls.checkPassword.updateValueAndValidity());
  }

  confirmationValidator = (control: UntypedFormControl): { [s: string]: boolean } => {
    if (!control.value) {
      return { required: true };
    } 
    // else if (control.value !== this.validateForm1.controls.password.value) {
    //   return { confirm: true, error: true };
    // }
    return {};
  };

  getCaptcha(e: MouseEvent): void {
    e.preventDefault();
  }
}
