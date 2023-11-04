import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, map, tap } from 'rxjs';
import { AssetsQueryReponse } from 'src/app/interfaces/assets-query-reponse';


@Injectable({
  providedIn: 'root'
})

export class TableService {
  constructor(private http: HttpClient) { }

  public getTableData$(
    pageIndex: number,
    pageSize: number,
    date: string,
  ): Observable<AssetsQueryReponse> {
    let params = new HttpParams()
      .append('pageNumber', pageIndex)
      .append('pageSize', pageSize)
      .append('date', date);

    // const params = { pageNumber: pageIndex = 1, pageSize: pageSize = 10, date: date = '2022-08-19' };
    const url = '/api/stocks';
    return this.http.get<AssetsQueryReponse>(url, { params }).pipe(
      tap(request => console.log(`Sending request: ${request}`)),
      tap(response => console.log(`Received response: ${response}`)),
    )
  }

  public getFundTableData$(
    pageIndex: number,
    pageSize: number,
    date: string,
  ): Observable<AssetsQueryReponse> {
    let params = new HttpParams()
      .append('pageNumber', pageIndex)
      .append('pageSize', pageSize)
      .append('date', date);

    // const params = { pageNumber: pageIndex = 1, pageSize: pageSize = 10, date: date = '2022-08-19' };
    const url = '/api/funds';
    return this.http.get<AssetsQueryReponse>(url, { params }).pipe(
      tap(request => console.log(`Sending request: ${request}`)),
      tap(response => console.log(`Received response: ${response}`)),
    )
  }

  public getBondTableData$(
    pageIndex: number,
    pageSize: number,
    date: string,
  ): Observable<AssetsQueryReponse> {
    let params = new HttpParams()
      .append('pageNumber', pageIndex)
      .append('pageSize', pageSize)
      .append('date', date);

    // const params = { pageNumber: pageIndex = 1, pageSize: pageSize = 10, date: date = '2022-08-19' };
    const url = '/api/bonds';
    return this.http.get<AssetsQueryReponse>(url, { params }).pipe(
      tap(request => console.log(`Sending request: ${request}`)),
      tap(response => console.log(`Received response: ${response}`)),
    )
  }

  public getUserHostStocks$(userId: number, date: string): Observable<[]> {
    const url = `/api/user/invest/stock/${userId}`;
    let params = new HttpParams().append('date', date);
    return this.http.get<[]>(url, { params }).pipe(
      tap(request => console.log(`Sending request: ${request}`)),
      tap(response => console.log(`Received response: ${response}`))
    )
  }

  public getUserHostFunds$(userId: number, date: string): Observable<[]> {
    const url = `/api/user/invest/fund/${userId}`;
    let params = new HttpParams().append('date', date);
    return this.http.get<[]>(url, { params }).pipe(
      tap(request => console.log(`Sending request: ${request}`)),
      tap(response => console.log(`Received response: ${response}`))
    )
  }

  public getUserHostBonds$(userId: number, date: string): Observable<[]> {
    const url = `/api/user/invest/bond/${userId}`;
    let params = new HttpParams().append('date', date);
    return this.http.get<[]>(url, { params }).pipe(
      tap(request => console.log(`Sending request: ${request}`)),
      tap(response => console.log(`Received response: ${response}`))
    )
  }

  public postUserSellData$(data: any): Observable<any> {
    const url = '/api/user/invest/sellStock';
    return this.http.post<any>(url, data).pipe(
      tap(request => console.log(`Sending post request: ${request}`)),
      tap(response => console.log(`Received response: ${response}`))
    )
  }

  public getUserHistoryDeal$(id: number): Observable<any> {
    const url = `/api/user/invest/userTransaction/${id}`;
    return this.http.get<any>(url).pipe(
      tap(request => console.log(`Sending post request: ${request}`)),
      tap(response => console.log(`Received response: ${response}`))
    )
  }

  public postUserBuyData$(data: any): Observable<any> {
    const url = '/api/user/invest/buyStock';
    return this.http.post<any>(url, data).pipe(
      tap(request => console.log(`Sending post request: ${request}`)),
      tap(response => console.log(`Received response: ${response}`))
    )
  }

  public postUserBuyFundData$(data: any): Observable<any> {
    const url = '/api/user/invest/buyFund';
    return this.http.post<any>(url, data).pipe(
      tap(request => console.log(`Sending post request: ${request}`)),
      tap(response => console.log(`Received response: ${response}`))
    )
  }

  public postUserBuyBondData$(data: any): Observable<any> {
    const url = '/api/user/invest/buyBond';
    return this.http.post<any>(url, data).pipe(
      tap(request => console.log(`Sending post request: ${request}`)),
      tap(response => console.log(`Received response: ${response}`))
    )
  }
}
