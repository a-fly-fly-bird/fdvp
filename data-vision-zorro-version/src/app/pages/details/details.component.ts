import { Component, OnInit } from '@angular/core';
import { ChartService } from 'src/app/shared/service/chart.service';
import { UserInvestmentTotal } from 'src/app/interfaces/user-investment-total';
import { Color, ScaleType } from '@swimlane/ngx-charts';
import { UserService } from 'src/app/shared/service/user.service';
import { GlobalService } from 'src/app/shared/service/global.service';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.scss']
})
export class DetailsComponent implements OnInit {

  userId: number;
  date: string;
  stockData: number;
  fundData: number;
  bondData: number;
  userInvestmentReview: UserInvestmentTotal[];
  totalRetunrnRate: number = 0;

  multi: any[] = [];
  // view: [number, number] = [700, 300];

  // options
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
  
  single = [
    {
      "name": "Stock",
      "value": 8940000
    },
    {
      "name": "Fund",
      "value": 5000000
    },
    {
      "name": "Bond",
      "value": 7200000
    },
  ];
  
  constructor(private chatService: ChartService, private userService: UserService, private globalService: GlobalService){
    this.date = this.globalService.getDate();
    this.userId = this.globalService.getUserId();
    this.stockData = 0;
    this.fundData = 0;
    this.bondData = 0;
    this.userInvestmentReview = [];
  }

  ngOnInit(): void {
    this.userId = this.globalService.getUserId();
    console.log('this.userId', this.userId);
    this.getUserInvestmentReview();
    this.getRevenueData();
    this.getUserTotalReview$(this.userId, this.date);
  }

  getUserInvestmentReview(): void {
    this.chatService.getUserInvestmentReview$(this.userId, this.date).subscribe(
      (data: UserInvestmentTotal[]) => {
        console.log('summary data:', data);
        this.userInvestmentReview = data;
        this.stockData = this.userInvestmentReview[0].returnOfRate;
        this.fundData = this.userInvestmentReview[1].returnOfRate;
        this.bondData = this.userInvestmentReview[2].returnOfRate;
        this.single[0].value = Math.floor(this.userInvestmentReview[0].sumMoney * 100) / 100;
        this.single[1].value = Math.floor(this.userInvestmentReview[1].sumMoney * 100) / 100;
        this.single[2].value = Math.floor(this.userInvestmentReview[2].sumMoney * 100) / 100;
        console.log('this.single', this.single);
        // oh. my. god. this works.
        this.single = [...this.single];
      });
  }

  getRevenueData(): void {
    this.chatService.getUserReveneueCurve$(this.userId, this.date).subscribe(
      (x: any) => {
        this.multi = x;
        console.log('this.multi', this.multi);
        // const transformedData = this.multi.map(item => {
        //   const series = Object.entries(item)
        //     .filter(([key, value]) => key !== "date" && value !== null)
        //     .map(([key, value]) => ({ name: key, value }));
        //   return {
        //     name: item.date,
        //     series
        //   };
        // }).filter(item => item.series.length > 0);
        // console.log(transformedData);
        // this.multi = transformedData;
        const transformedData = Object.keys(this.multi[0]).filter(key => key !== "date").map(key => {
          return {
            name: key,
            series: this.multi.map(item => {
              return {
                name: item.date.split("T")[0],
                value: item[key] || 0
              };
            })
          };
        });
        this.multi = transformedData;
        console.log(transformedData);
      }
    );
  };

  onSelect(data: any): void {
    console.log('Item clicked', JSON.parse(JSON.stringify(data)));
  }

  onActivate(data: any): void {
    console.log('Activate', JSON.parse(JSON.stringify(data)));
  }

  onDeactivate(data: any): void {
    console.log('Deactivate', JSON.parse(JSON.stringify(data)));
  }

  public getUserTotalReview$(userid: number, date: string): void{
    this.userService.getUserTotalReturnRate$(userid, date).subscribe(
      (data: any) => {
        console.log('data', data);
        this.totalRetunrnRate = data;
      }
    );
  }

  view1: [number, number] = [460, 200];
  view2: [number, number] = [1050, 360];
  // options
  gradient: boolean = true;
  showLegend: boolean = true;
  showLabels1: boolean = true;
  isDoughnut: boolean = false;

  onSelect1(data: any): void {
    console.log('Item clicked', JSON.parse(JSON.stringify(data)));
  }

  onActivate1(data: any): void {
    console.log('Activate', JSON.parse(JSON.stringify(data)));
  }

  onDeactivate1(data: any): void {
    console.log('Deactivate', JSON.parse(JSON.stringify(data)));
  }
}
