import { GlobalService } from './../../../shared/service/global.service';
import { Component, OnInit } from '@angular/core';
import { TableService } from 'src/app/shared/service/table.service';
import { NzTableFilterFn, NzTableFilterList, NzTableQueryParams, NzTableSortFn, NzTableSortOrder } from 'ng-zorro-antd/table';
import { SellPostData } from 'src/app/interfaces/sell-post-data';
import { Color, ScaleType } from '@swimlane/ngx-charts';
import { ChartService } from 'src/app/shared/service/chart.service';

interface ColumnItem {
  nzShowSort: boolean;
  name: string;
  sortOrder: NzTableSortOrder | null;
  sortFn: NzTableSortFn<any> | null;
  listOfFilter: NzTableFilterList;
  filterFn: NzTableFilterFn<any> | null;
  filterMultiple: boolean;
  sortDirections: NzTableSortOrder[];
}

@Component({
  selector: 'app-sell',
  templateUrl: './sell.component.html',
  styleUrls: ['./sell.component.scss']
})

export class SellComponent implements OnInit{
  loading: boolean;
  pageIndex: number;
  pageSize: number;
  total: number;
  date: string;
  listOfAssetsData: any[] = [];
  earn_lose_divider = 100;
  isVisible = false;
  isOkLoading = false;
  sell_number: number;

  selected_data: any;

  sell_post_data: SellPostData = {
    balance: 0,
    id: 1,
    productCode: 'AAPL',
    productType: 'STOCK',
    transactionAmount: 100,
    transactionDate: this.globalService.getDate(),
    transactionPrice: 10,
    transactionType: 'SELL',
    userId: 1,
    userName: 'Bob'
  };

  multi: any[] = [];
  legend: boolean = true;
  showLabels: boolean = true;
  animations: boolean = true;
  xAxis: boolean = true;
  yAxis: boolean = true;
  showYAxisLabel: boolean = true;
  showXAxisLabel: boolean = true;
  xAxisLabel: string = 'Time';
  yAxisLabel: string = 'Rate';
  timeline: boolean = true;
  autoScale: boolean = true;

  colorScheme: Color = {
    name: 'myScheme',
    selectable: true,
    group: ScaleType.Ordinal,
    domain: ['#5AA454', '#E44D25', '#CFC0BB', '#7aa3e5', '#a8385d', '#aae3f5'],
  };

  cardColor: string = '#232837';
  
  public getStockHistory(code: string) {
    console.log('code', code)
    this.chartService.getStockDetail$(code).subscribe(
      (data) => {
        console.log('history_data', data);
        // this.history_data = data;

        // gpt 真的好强！
        const transformedData = data.reduce((result, asset) => {
          const existingItem = result.find(item => item.name === asset.name);
          const seriesItem = {
            name: asset.date,
            value: asset.adjClose
          };

          if (existingItem) {
            existingItem.series.push(seriesItem);
          } else {
            result.push({
              name: asset.name,
              series: [seriesItem]
            });
          }
          return result;
        }, [] as { name: string, series: { name: string, value: number }[] }[]);

        this.multi = transformedData;
        console.log(this.multi);
      }
    );
  }

  onSelectLine(data: any): void {
    console.log('Item clicked', JSON.parse(JSON.stringify(data)));
  }

  onActivate(data: any): void {
    console.log('Activate', JSON.parse(JSON.stringify(data)));
  }

  onDeactivate(data: any): void {
    console.log('Deactivate', JSON.parse(JSON.stringify(data)));
  }

  isVisible1 = false;
  isOkLoading1 = false;
  selected_data1: any;

  showModal1(data: any): void {
    this.isVisible1 = true;
    this.selected_data1 = data;
    this.getStockHistory(data.stockName);
    console.log('selected data', this.selected_data1)
    console.log('multi', this.multi)
    this.multi = [... this.multi]
  }

  handleOk1(): void {
    this.isOkLoading1 = true;
    setTimeout(() => {
      this.isVisible1 = false;
      this.isOkLoading1 = false;
    }, 100);
  }

  handleCancel1(): void {
    this.isVisible1 = false;
  }

  constructor(private tableService: TableService, private chartService: ChartService, private globalService: GlobalService) {
    this.loading = false;
    this.pageIndex = 1;
    this.pageSize = 10;
    this.total = 100;
    this.date = this.globalService.getDate();
    this.sell_number = 1;
  }

  ngOnInit(): void {
    this.sell_post_data.userId = this.globalService.getUserId();
    this.sell_post_data.userName = this.globalService.getUserName();
    this.date = this.globalService.getDate();
    this.getHostData(this.sell_post_data.userId, this.date)
  }

  public postSellData(data: SellPostData){
    this.tableService.postUserSellData$(data).subscribe(
      (x) => {
        console.log(x);
        this.getHostData(this.sell_post_data.userId, this.date);
      }
    )
  }
  
  public getHostData(id: number, date: string){
    this.loading = true;
    this.tableService.getUserHostStocks$(id, date).subscribe(
      data => {
        console.log(data);
        this.total = data?.length;
        this.listOfAssetsData = data;
        this.loading = false;
      },
      error => {
        console.log(error);
        this.loading = false;
      }
    )
  }

  showModal(data: any): void {
    this.selected_data = data;
    this.isVisible = true;
  }

  handleOk(): void {
    this.isOkLoading = true;
    // this.postSellData(this.selected_data);
    console.log('selected data', this.selected_data)
    this.sell_post_data.productCode = this.selected_data.stockCode;
    this.sell_post_data.productType = 'STOCK';
    this.sell_post_data.transactionAmount = this.sell_number;
    this.sell_post_data.transactionPrice = this.selected_data.currentPrice;
    this.sell_post_data.id = this.selected_data.id;
    this.sell_post_data.userId = this.globalService.getUserId();
    this.sell_post_data.userName = this.globalService.getUserName();
    this.postSellData(this.sell_post_data);
    setTimeout(() => {
      this.isVisible = false;
      this.isOkLoading = false;
    }, 1000)
  }

  handleCancel(): void {
    this.isVisible = false;
  }

  onQueryParamsChange(params: NzTableQueryParams): void {
    console.log(params);
    const { pageSize, pageIndex, sort, filter } = params;
    this.loading = true;
    this.tableService.getTableData$(pageIndex, pageSize, this.date).subscribe(
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
      nzShowSort: true,
      sortOrder: null,
      sortFn: (a: any, b: any) => a.name.localeCompare(b.name),
      sortDirections: ['ascend', 'descend', null],
      filterMultiple: true,
      listOfFilter: [],
      filterFn: (list: string[], item: any) => list.some(name => item.name.indexOf(name) !== -1)
    },
    {
      name: 'Buy Price',
      nzShowSort: true,
      sortOrder: null,
      // sortFn: (a: Assets, b: Assets) => a.age - b.age,
      sortFn: null,
      sortDirections: ['ascend', 'descend', null],
      listOfFilter: [],
      filterFn: null,
      filterMultiple: true
    },
    {
      name: 'Buy Date',
      nzShowSort: true,
      sortOrder: null,
      sortDirections: ['ascend', 'descend', null],
      sortFn: (a: any, b: any) => a.open - b.open,
      filterMultiple: false,
      listOfFilter: [
        { text: 'Earn', value: 'Earn' },
        { text: 'Lose', value: 'Lose', byDefault: false }
      ],
      filterFn: (a: any, b:any) => {
        if(a === 'Earn') {
          return b.buyPrice <= b.currentPrice
        }
        return b.buyPrice > b.currentPrice
      }
    },
    {
      name: 'Current Price',
      nzShowSort: true,
      sortOrder: null,
      sortDirections: ['ascend', 'descend', null],
      sortFn: (a: any, b: any) => a.high - b.high,
      filterMultiple: false,
      listOfFilter: [],
      filterFn: null
    },
    {
      name: 'Position Number',
      nzShowSort: true,
      sortOrder: null,
      sortDirections: ['ascend', 'descend', null],
      sortFn: (a: any, b: any) => a.high - b.high,
      filterMultiple: false,
      listOfFilter: [],
      filterFn: null
    },
    {
      name: 'Rate Of Return',
      nzShowSort: true,
      sortOrder: null,
      sortDirections: ['ascend', 'descend', null],
      sortFn: (a: any, b: any) => a.high - b.high,
      filterMultiple: false,
      listOfFilter: [],
      filterFn: null
    },
    {
      name: 'Button',
      nzShowSort: false,
      sortOrder: null,
      sortDirections: ['ascend', 'descend', null],
      sortFn: (a: any, b: any) => a.high - b.high,
      filterMultiple: false,
      listOfFilter: [],
      filterFn: null
    }
  ]
}
