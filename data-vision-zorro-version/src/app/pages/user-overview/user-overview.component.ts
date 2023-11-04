import { Component, OnInit } from '@angular/core';
import { NzTableFilterFn, NzTableFilterList, NzTableQueryParams, NzTableSortFn, NzTableSortOrder } from 'ng-zorro-antd/table';
import { GlobalService } from 'src/app/shared/service/global.service';
import { TableService } from 'src/app/shared/service/table.service';

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
  selector: 'app-user-overview',
  templateUrl: './user-overview.component.html',
  styleUrls: ['./user-overview.component.scss']
})
export class UserOverviewComponent implements OnInit {
  id = 1;
  loading: boolean;
  pageIndex: number;
  pageSize: number;
  total: number;
  date: string;
  listOfAssetsData: any[] = [];
  shownData: any[] = [];

  constructor(private tableService: TableService, private globalService: GlobalService) {
    this.loading = false;
    this.pageIndex = 1;
    this.pageSize = 10;
    this.total = 100;
    this.date = this.globalService.getDate();
  }

  ngOnInit(): void {
    this.id = this.globalService.getUserId();
    this.getHistoryData(this.id);
  }

  public getHistoryData(id: number) {
    this.tableService.getUserHistoryDeal$(id).subscribe((data) => {
      this.listOfAssetsData = data;
      this.total = data.length;
      console.log('history data: ', data);
      this.loadPageData();
    })
  }

  handlePageIndexChange(event: number) {
    console.log('新的分页索引值：', event);
    // 更新分页索引值
    this.pageIndex = event;

    // 加载对应的数据
    this.loadPageData();
  }

  handlePageSizeChange(event: number) {
    console.log('新的每页显示条数：', event); 
    // 更新每页显示条数
    this.pageSize = event;
    // 加载对应的数据
    this.loadPageData();
  }

  loadPageData(): void {
    // 根据当前的分页索引值和每页显示的数据条数计算起始索引和结束索引
    const startIndex = (this.pageIndex - 1) * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    
    // 根据起始索引和结束索引从数据源中截取对应的数据
    this.shownData = this.listOfAssetsData.slice(startIndex, endIndex);
  }

  // 当服务端分页、筛选、排序时，用于获得参数, 这里客户端分页，所以用不上
  onQueryParamsChange(params: NzTableQueryParams): void {
    console.log('query params: ', params);
    const { pageSize, pageIndex, sort, filter } = params;
    this.loading = true;
    // this.tableService.getTableData$(pageIndex, pageSize, this.date).subscribe(
    //   data => {
    //     console.log(data);
    //     this.total = data.totalElements;
    //     this.listOfAssetsData = data.content;
    //     this.loading = false;
    //   }
    // );
  }

  listofAssetsDataColumns: ColumnItem[] = [
    {
      name: 'ID',
      nzShowSort: true,
      sortOrder: null,
      sortFn: (a: any, b: any) => a.name.localeCompare(b.name),
      sortDirections: ['ascend', 'descend', null],
      filterMultiple: true,
      listOfFilter: [],
      filterFn: (list: string[], item: any) => list.some(name => item.name.indexOf(name) !== -1)
    },
    {
      name: 'Product Code',
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
      name: 'Product Type',
      nzShowSort: true,
      sortOrder: null,
      sortDirections: ['ascend', 'descend', null],
      sortFn: (a: any, b: any) => a.open - b.open,
      filterMultiple: true,
      listOfFilter: [
        { text: 'Stock', value: 'Stock' },
        { text: 'Fund', value: 'Fund'},
        { text: 'Bond', value: 'Bond'}
      ],
      filterFn: (a: any, b: any) => {
        if (a === 'Earn') {
          return b.buyPrice <= b.currentPrice
        }
        return b.buyPrice > b.currentPrice
      }
    },
    {
      name: 'Transaction Amount',
      nzShowSort: true,
      sortOrder: null,
      sortDirections: ['ascend', 'descend', null],
      sortFn: (a: any, b: any) => a.transactionAmount - b.transactionAmount,
      filterMultiple: false,
      listOfFilter: [],
      filterFn: null
    },
    {
      name: 'Transaction Price',
      nzShowSort: true,
      sortOrder: null,
      sortDirections: ['ascend', 'descend', null],
      sortFn: (a: any, b: any) => a.transactionPrice - b.transactionPrice,
      filterMultiple: false,
      listOfFilter: [],
      filterFn: null
    },
    {
      name: 'Transaction Type',
      nzShowSort: false,
      sortOrder: null,
      sortDirections: ['ascend', 'descend', null],
      sortFn: (a: any, b: any) => a.high - b.high,
      filterMultiple: false,
      listOfFilter: [],
      filterFn: null
    },
    {
      name: 'Transaction Date',
      nzShowSort: true,
      sortOrder: null,
      sortDirections: ['ascend', 'descend', null],
      sortFn: (a: any, b: any) => Date.parse(a.transactionDate) - Date.parse(b.transactionDate),
      filterMultiple: false,
      listOfFilter: [],
      filterFn: null
    },
    {
      name: 'Balance',
      nzShowSort: true,
      sortOrder: null,
      sortDirections: ['ascend', 'descend', null],
      sortFn: (a: any, b: any) => a.balance - b.balance,
      filterMultiple: false,
      listOfFilter: [],
      filterFn: null
    }
  ]
}
