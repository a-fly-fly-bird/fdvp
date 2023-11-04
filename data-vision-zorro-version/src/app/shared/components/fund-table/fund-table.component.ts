import { Component, OnInit } from '@angular/core';
import { NzTableFilterFn, NzTableFilterList, NzTableSortFn, NzTableSortOrder } from 'ng-zorro-antd/table';
import { TableService } from '../../service/table.service';
import { Assets } from 'src/app/interfaces/assets';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { SellPostData } from 'src/app/interfaces/sell-post-data';
import { Router } from '@angular/router';
import { GlobalService } from '../../service/global.service';

interface ColumnItem {
  name: string;
  sortOrder: NzTableSortOrder | null;
  sortFn: NzTableSortFn<Assets> | null;
  listOfFilter: NzTableFilterList;
  filterFn: NzTableFilterFn<Assets> | null;
  filterMultiple: boolean;
  sortDirections: NzTableSortOrder[];
}

@Component({
  selector: 'app-fund-table',
  templateUrl: './fund-table.component.html',
  styleUrls: ['./fund-table.component.scss']
})
export class FundTableComponent {
  loading: boolean;
  pageIndex: number;
  pageSize: number;
  total: number;
  date: any;

  earn_lose_divider = 0;
  isVisible = false;
  isOkLoading = false;
  buy_number = 0;
  selected_data: any;

  onChange(result: Date): void {
    // this.globalService.setDate(this.date);
    console.log('onChange: ', result);
    // this.loading = true;
    // this.getHostData(1, this.date);
    var year = this.date.getFullYear();
    var month = ("0" + (this.date.getMonth() + 1)).slice(-2);
    var day = ("0" + this.date.getDate()).slice(-2);

    var dateString = year + "-" + month + "-" + day;
    this.globalService.setDate(dateString);
    this.tableService.getFundTableData$(this.pageIndex, this.pageSize, dateString).subscribe(
      data => {
        console.log(data);
        this.total = data.totalElements;
        this.listOfAssetsData = data.content;
        this.loading = false;
      }
    );
  }

  buy_post_data: SellPostData = {
    balance: 0,
    id: 1,
    productCode: 'WWNPX',
    productType: 'FUND',
    transactionAmount: 100,
    transactionDate: '2022-08-29',
    transactionPrice: 10,
    transactionType: 'BUY',
    userId: 1,
    userName: 'Bob'
  };

  showModal(data: any): void {
    this.selected_data = data;
    this.isVisible = true;
  }

  handleOk(): void {
    this.isOkLoading = true;
    this.buy_post_data.productCode = this.selected_data.fundCode;
    this.buy_post_data.productType = 'FUND';
    this.buy_post_data.transactionAmount = this.buy_number;
    console.log('this.selected_data.currentPrice', this.selected_data.adjClose)
    this.buy_post_data.transactionPrice = this.selected_data.adjClose;
    this.buy_post_data.id = this.selected_data.id;
    this.buy_post_data.userId = this.globalService.getUserId();
    this.buy_post_data.userName = this.globalService.getUserName();
    console.log('this.globalService.getDate()', this.globalService.getDate())
    this.buy_post_data.transactionDate = this.globalService.getDate();
    console.log('this.buy_post_data', this.buy_post_data);
    this.postBuyFundData(this.buy_post_data);
    setTimeout(() => {
      this.isVisible = false;
      this.isOkLoading = false;
      this.route.navigate(['/dashboard/operation/sell_fund']);
    }, 1000);
  }

  handleCancel(): void {
    this.isVisible = false;
  }
  
  public postBuyFundData(data: SellPostData){
    this.tableService.postUserBuyFundData$(data).subscribe(
      (x) => {
        this.loading = true;
        console.log(x);
        // this.getHostData(1, this.date);
        this.tableService.getFundTableData$(this.pageIndex, this.pageSize, data.transactionDate).subscribe(
          data => {
            console.log(data);
            this.total = data.totalElements;
            this.listOfAssetsData = data.content;
            this.loading = false;
          }
        );
      }
    )
  }
  constructor(private tableService: TableService, private route: Router, private globalService: GlobalService) {
    this.loading = false;
    this.pageIndex = 1;
    this.pageSize = 10;
    this.total = 100;
    this.date = this.globalService.getDate();
  }

  ngOnInit(): void {
    this.loading = true;
    this.buy_post_data.userId = this.globalService.getUserId();
    this.buy_post_data.userName = this.globalService.getUserName();
    this.tableService.getFundTableData$(this.pageIndex, this.pageSize, this.date).subscribe(
      data => {
        console.log(data);
        this.total = data.totalElements;
        this.listOfAssetsData = data.content;
        this.loading = false;
      }
    );
  }

  onQueryParamsChange(params: NzTableQueryParams): void {
    console.log(params);
    const { pageSize, pageIndex, sort, filter } = params;
    this.loading = true;
    this.tableService.getFundTableData$(pageIndex, pageSize, this.date).subscribe(
      data => {
        console.log(data);
        this.total = data.totalElements;
        this.listOfAssetsData = data.content;
        this.loading = false;
      }
    );
  }

  listofAssetsDataColumns: ColumnItem[] = [
    {
      name: 'Name',
      sortOrder: null,
      sortFn: (a: Assets, b: Assets) => a.name.localeCompare(b.name),
      sortDirections: ['ascend', 'descend', null],
      filterMultiple: true,
      listOfFilter: [],
      filterFn: (list: string[], item: Assets) => list.some(name => item.name.indexOf(name) !== -1)
    },
    {
      name: 'Date',
      sortOrder: null,
      // sortFn: (a: Assets, b: Assets) => a.age - b.age,
      sortFn: null,
      sortDirections: ['ascend', 'descend', null],
      listOfFilter: [],
      filterFn: null,
      filterMultiple: true
    },
    {
      name: 'Open',
      sortOrder: null,
      sortDirections: ['ascend', 'descend', null],
      sortFn: (a: Assets, b: Assets) => a.open - b.open,
      filterMultiple: false,
      listOfFilter: [
        { text: 'Earn', value: 'Earn' },
        { text: 'Lose', value: 'Lose', byDefault: false }
      ],
      filterFn: (a: any, b: any) => {
        if (a === 'Earn') {
          return b.open > this.earn_lose_divider
        }
        return b.open < this.earn_lose_divider
      }
    },
    {
      name: 'High',
      sortOrder: null,
      sortDirections: ['ascend', 'descend', null],
      sortFn: (a: Assets, b: Assets) => a.high - b.high,
      filterMultiple: false,
      listOfFilter: [],
      filterFn: null
    },
    {
      name: 'Low',
      sortOrder: null,
      sortDirections: ['ascend', 'descend', null],
      sortFn: (a: Assets, b: Assets) => a.high - b.high,
      filterMultiple: false,
      listOfFilter: [],
      filterFn: null
    },
    {
      name: 'Close',
      sortOrder: null,
      sortDirections: ['ascend', 'descend', null],
      sortFn: (a: Assets, b: Assets) => a.high - b.high,
      filterMultiple: false,
      listOfFilter: [],
      filterFn: null
    },
    {
      name: 'Adj Close',
      sortOrder: null,
      sortDirections: ['ascend', 'descend', null],
      sortFn: (a: Assets, b: Assets) => a.high - b.high,
      filterMultiple: false,
      listOfFilter: [],
      filterFn: null
    },
    // {
    //   name: 'Volume',
    //   sortOrder: null,
    //   sortDirections: ['ascend', 'descend', null],
    //   sortFn: (a: Assets, b: Assets) => a.high - b.high,
    //   filterMultiple: false,
    //   listOfFilter: [],
    //   filterFn: null
    // },
    {
      name: 'Operation',
      sortOrder: null,
      sortDirections: ['ascend', 'descend', null],
      sortFn: (a: Assets, b: Assets) => a.high - b.high,
      filterMultiple: false,
      listOfFilter: [],
      filterFn: null
    }
  ]

  listOfAssetsData: Assets[] = [
    {
      "name": "AAPL",
      "stockCode": "AAPL",
      "date": "2022-08-19",
      "open": 173.02999877929688,
      "high": 173.74000549316406,
      "low": 171.30999755859375,
      "close": 171.52000427246094,
      "adjClose": 170.5084686279297,
      "volume": 70346300,
      "roi": 0,
      "id": 5196,
    },
    {
      "name": "TSLA",
      "stockCode": "TSLA",
      "date": "2022-08-19",
      "open": 299,
      "high": 300.3599853515625,
      "low": 292.5,
      "close": 296.6666564941406,
      "adjClose": 296.6666564941406,
      "volume": 61395300,
      "roi": 0,
      "id": 5447,
    },
    {
      "name": "AMD",
      "stockCode": "AMD",
      "date": "2022-08-19",
      "open": 98.66999816894531,
      "high": 99.25,
      "low": 94.58999633789062,
      "close": 95.94999694824219,
      "adjClose": 95.94999694824219,
      "volume": 67221700,
      "roi": 0,
      "id": 5698,
    },
    {
      "name": "MARA",
      "stockCode": "MARA",
      "date": "2022-08-19",
      "open": 13.880000114440918,
      "high": 14.359999656677246,
      "low": 13.050000190734863,
      "close": 13.180000305175781,
      "adjClose": 13.180000305175781,
      "volume": 16042600,
      "roi": 0,
      "id": 5949,
    },
    {
      "name": "CCL",
      "stockCode": "CCL",
      "date": "2022-08-19",
      "open": 10.140000343322754,
      "high": 10.239999771118164,
      "low": 9.699999809265137,
      "close": 9.869999885559082,
      "adjClose": 9.869999885559082,
      "volume": 50379400,
      "roi": 0,
      "id": 6200,
    },
    {
      "name": "PLUG",
      "stockCode": "PLUG",
      "date": "2022-08-19",
      "open": 27.239999771118164,
      "high": 27.489999771118164,
      "low": 26.280000686645508,
      "close": 26.81999969482422,
      "adjClose": 26.81999969482422,
      "volume": 19268200,
      "roi": 0,
      "id": 6451,
    },
  ]
}

