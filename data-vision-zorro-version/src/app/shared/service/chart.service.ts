import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, map, tap } from 'rxjs';
import { UserInvestmentTotal } from 'src/app/interfaces/user-investment-total';
import { ReveneueReturn } from 'src/app/interfaces/reveneue-return';
import { Assets } from 'src/app/interfaces/assets';

@Injectable({
  providedIn: 'root'
})
export class ChartService {

  constructor(private http: HttpClient) { }

  public getUserInvestmentReview$(userId: number, date: string): Observable<UserInvestmentTotal[]> {
    let url: string = `/api/user/invest/total/${userId}`;
    let params = new HttpParams()
      .append('date', date);
    return this.http.get<UserInvestmentTotal[]>(url, { params }).pipe(
      tap(request => console.log(`Sending request: ${request}`)),
      tap(response => console.log(`Received response: ${response}`))
    )
  }

  public getUserReveneueCurve$(userId: number, date: string): Observable<ReveneueReturn> {
    const url = `/api/user/invest/reveneue_curve/${userId}`;
    let params = new HttpParams()
      // .append('userId', userId)
      .append('date', date);
    return this.http.get<ReveneueReturn>(url, { params }).pipe(
      tap(request => console.log(`Sending request: ${request}`)),
      tap(response => console.log(`Received response: ${response}`))
    )
  }

  public getStockDetail$(code: string): Observable<Assets[]> {
    const url = `/api/stocks/stock_details/${code}`;
    return this.http.get<Assets[]>(url).pipe(
      tap(request => console.log(`Sending request: ${request}`)),
      tap(response => console.log(`Received response: ${response}`))
    );
  }

  public getFundDetail$(code: string): Observable<Assets[]> {
    const url = `/api/funds/funds_details/${code}`;
    return this.http.get<Assets[]>(url).pipe(
      tap(request => console.log(`Sending request: ${request}`)),
      tap(response => console.log(`Received response: ${response}`))
    );
  }

  public getBondDetail$(code: string): Observable<Assets[]> {
    const url = `/api/bonds/bonds_details/${code}`;
    return this.http.get<Assets[]>(url).pipe(
      tap(request => console.log(`Sending request: ${request}`)),
      tap(response => console.log(`Received response: ${response}`))
    );
  }

  public getStockPriceChange$(code: string, date: string, period: number): Observable<any> {
    const url = `/api/stocks/stocks_price_change/${code}`;
    let params = new HttpParams()
        .append('date', date)
        .append('period', period.toString());
    console.log('params', params);
    return this.http.get<any>(url, { params }).pipe(
      tap(request => console.log(`Sending request: ${request}`)),
      tap(response => console.log(`Received response: ${response}`))
    );
  }

  public getFundPriceChange$(code: string, date: string, period: number): Observable<any> {
    const url = `/api/funds/funds_price_change/${code}`;
    let params = new HttpParams().append('date', date).append('period', period.toString());
    return this.http.get<any>(url, { params }).pipe(
      tap(request => console.log(`Sending request: ${request}`)),
      tap(response => console.log(`Received response: ${response}`))
    );
  }

  public getBondPriceChange$(code: string, date: string, period: number): Observable<any> {
    const url = `/api/bonds/bonds_price_change/${code}`;
    let params = new HttpParams().append('date', date).append('period', period.toString());
    return this.http.get<any>(url, { params }).pipe(
      tap(request => console.log(`Sending request: ${request}`)),
      tap(response => console.log(`Received response: ${response}`))
    );
  }

  public getStockPriceStability$(code: string, date: string, period: number): Observable<any> {
    const url = `/api/stocks/stocks_price_stability/${code}`;
    let params = new HttpParams().append('date', date).append('period', period.toString());
    return this.http.get<any>(url, { params }).pipe(
      tap(request => console.log(`Sending request: ${request}`)),
      tap(response => console.log(`Received response: ${response}`))
    );
  }

  public getFundPriceStability$(code: string, date: string, period: number): Observable<any> {
    const url = `/api/funds/funds_price_stability/${code}`;
    let params = new HttpParams().append('date', date).append('period', period.toString());
    return this.http.get<any>(url, { params }).pipe(
      tap(request => console.log(`Sending request: ${request}`)),
      tap(response => console.log(`Received response: ${response}`))
    );
  }

  public getBondPriceStability$(code: string, date: string, period: number): Observable<any> {
    const url = `/api/bonds/bonds_price_stability/${code}`;
    let params = new HttpParams().append('date', date).append('period', period.toString());
    return this.http.get<any>(url, { params }).pipe(
      tap(request => console.log(`Sending request: ${request}`)),
      tap(response => console.log(`Received response: ${response}`))
    );
  }
}
