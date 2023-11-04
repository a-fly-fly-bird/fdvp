import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, map, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class UserService {

  constructor(private http: HttpClient) { }

  public addUser$(data: any): Observable<any>{
    const url = '/api/user/addUser';
    return this.http.post<any>(url, data).pipe(
      tap(request => console.log(`Sending request: ${request}`)),
      tap(response => console.log(`Received response: ${response}`))
    )
  }

  public getUserData$(name: string, passwd: string): Observable<any>{
    // let params = new HttpParams()
    //   .append('name', name)
    //   .append('passWord', passwd)
    const url = `/api/user/login/${name}/${passwd}`;
    return this.http.get<any>(url).pipe(
      tap(request => console.log(`Sending request: ${request}`)),
      tap(response => console.log(`Received response: ${response}`))
    )
  }

  public getUserTotalReturnRate$(userid: number, date: string): Observable<any>{
    const url = `/api/user/invest/total_rate_return/${userid}`;
    let params = new HttpParams().append('date', date);
    return this.http.get<any>(url, {params}).pipe(
      tap(request => console.log(`Sending request: ${request}`)),
      tap(response => console.log(`Received response: ${response}`))
    )
  }
  
}
