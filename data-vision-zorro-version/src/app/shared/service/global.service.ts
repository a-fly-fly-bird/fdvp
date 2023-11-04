import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GlobalService {

  constructor(private http: HttpClient) { }

  userId: number = 1;
  userName: string = 'Nikunj';
  choose_date: string = '2023-08-22';

  setUserId(userId: number) {
    localStorage.setItem('userId', JSON.stringify(userId));
    this.userId = userId;
  }

  getUserId(): number {
    const data = localStorage.getItem('userId');
    return data ? JSON.parse(data) : null
  }

  setUserName(userName: string) {
    localStorage.setItem('userName', JSON.stringify(userName));
    this.userName = userName;
  }

  getUserName(): string {
    const data = localStorage.getItem('userName');
    return data ? JSON.parse(data) : null
  }

  setDate(choose_date: string) {
    localStorage.setItem('choose_date', JSON.stringify(choose_date));
    this.choose_date = choose_date;
  }

  getDate(): string {
    const data = localStorage.getItem('choose_date');
    return data ? JSON.parse(data) : null
  } 
}
