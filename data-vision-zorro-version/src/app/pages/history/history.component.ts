import { Assets } from './../../interfaces/assets';
import { Component, DoCheck, OnInit } from '@angular/core';
import { Color, ScaleType } from '@swimlane/ngx-charts';
import { ChartService } from 'src/app/shared/service/chart.service';
import { ActivatedRoute } from '@angular/router';
import { GlobalService } from 'src/app/shared/service/global.service';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.scss']
})

export class HistoryComponent implements OnInit, DoCheck {

  code: string = 'AAPL';

  date: any;

  single1 = [
    {
      "name": "Price Change Rate",
      "value": '10.0%'
    },
  ];

  single2 = [
    {
      "name": "Stability Assess",
      "value": 'Middle'
    },
  ];

  period_time = 365;

  history_data: Assets[] = [];

  number_view: [number, number] = [200, 200];

  multi: any[] = [];

  // options
  legend: boolean = true;
  showLabels: boolean = true;
  animations: boolean = true;
  xAxis: boolean = true;
  yAxis: boolean = true;
  showYAxisLabel: boolean = true;
  showXAxisLabel: boolean = true;
  xAxisLabel: string = 'Time';
  yAxisLabel: string = 'Price';
  timeline: boolean = true;
  autoScale: boolean = true;

  colorScheme: Color = {
    name: 'myScheme',
    selectable: true,
    group: ScaleType.Ordinal,
    domain: ['#5AA454', '#E44D25', '#CFC0BB', '#7aa3e5', '#a8385d', '#aae3f5'],
  };

  cardColor: string = '#232837';

  code_from_home: string = 'AAPL';
  catagory: string = 'stock';

  constructor(private chartService: ChartService, private routeInfo: ActivatedRoute, private globalService: GlobalService) { }

  ngOnInit(): void {
    this.date = this.globalService.getDate();
    this.code_from_home = this.routeInfo.snapshot.queryParams["assets_name"];
    console.log('this.code_from_home', this.code_from_home);
    console.log('this.catagory', this.catagory)
    this.catagory = this.routeInfo.snapshot.queryParams["catagory"];
    this.code = this.code_from_home;
    if (this.catagory == 'stock') {
      console.log('exec stock')
      this.getStockHistory(this.code);
      this.getStockPriceChange(this.code, this.date, this.period_time);
      this.getStockRisk(this.code, this.date, this.period_time);
    } else if (this.catagory == 'fund') {
      this.getFundHistory(this.code);
      this.getFundPricveChange(this.code, this.date, this.period_time);
      this.getFundRisk(this.code, this.date, this.period_time);
    } else if (this.catagory == 'bond') {
      this.getBondHistory(encodeURIComponent(this.code));
      this.getBondPriceChange(encodeURIComponent(this.code), this.date, this.period_time);
      this.getBondRisk(encodeURIComponent(this.code), this.date, this.period_time);
    }
    this.single1 = [...this.single1];
    this.single2 = [...this.single2];
  }

  ngDoCheck(): void {
    // this.single1 = [...this.single1];
    // this.single2 = [...this.single2];
    // console.log('this.single1', this.single1);
    // console.log('this.single2', this.single2);
  }

  onSelect(event: any) {
    console.log(event);
  }

  public getStockPriceChange(code: string, date: string, period: number) {
    this.chartService.getStockPriceChange$(code, date, period).subscribe(
      (data) => {
        console.log('getStockPriceChange data', data);
        this.single1 = [
          {
            "name": "Price Change Rate",
            "value": Math.floor(data * 100) / 100 + '%'
          },
        ];
        console.log('this.single1', this.single1);
        this.single1 = [...this.single1];
      }
    );
  }

  public getFundPricveChange(code: string, date: string, period: number) {
    this.chartService.getFundPriceChange$(code, date, period).subscribe(
      (data) => {
        console.log(data);
        this.single1 = [
          {
            "name": "Price Change Rate",
            "value": Math.floor(data * 100) / 100 + '%'
          },
        ];
        this.single1 = [...this.single1];
      }
    );
  }

  public getBondPriceChange(code: string, date: string, period: number) {
    this.chartService.getBondPriceChange$(code, date, period).subscribe(
      (data) => {
        console.log(data);
        this.single1 = [
          {
            "name": "Price Change Rate",
            "value": Math.floor(data * 100) / 100 + '%'
          },
        ];
        this.single1 = [...this.single1];
      }
    );
  }

  public getStockRisk(code: string, date: string, period: number){
    this.chartService.getStockPriceStability$(code, date, period).subscribe(
      (data) => {
        console.log(data);
        let risk = '';
        switch (data) {
          case 0:
            risk = 'Safe';
            break;
          case 1:
            risk = 'Low';
            break;
          case 2:
            risk = 'Middle';
            break;
          case 3:
            risk = 'High';
            break;
          default:
            risk = 'Middle';
            break;
        }

        this.single2 = [
          {
            "name": "Stability Assess",
            "value": risk
          },
        ];
        this.single2 = [...this.single2];
      }
    );
  }

  public getFundRisk(code: string, date: string, period: number){
    this.chartService.getFundPriceStability$(code, date, period).subscribe(
      (data) => {
        console.log(data);
        let risk = '';
        switch (data) {
          case 0:
            risk = 'Safe';
            break;
          case 1:
            risk = 'Low';
            break;
          case 2:
            risk = 'Middle';
            break;
          case 3:
            risk = 'High';
            break;
          default:
            risk = 'Middle';
            break;
        }
        this.single2 = [
          {
            "name": "Stability Assess",
            "value": risk
          },
        ];
        this.single2 = [...this.single2];
      }
    );
  }

  public getBondRisk(code: string, date: string, period: number){
    this.chartService.getBondPriceStability$(code, date, period).subscribe(
      (data) => {
        console.log(data);
        let risk = '';
        switch (data) {
          case 0:
            risk = 'Safe';
            break;
          case 1:
            risk = 'Low';
            break;
          case 2:
            risk = 'Middle';
            break;
          case 3:
            risk = 'High';
            break;
          default:
            risk = 'Middle';
            break;
        }
        this.single2 = [
          {
            "name": "Stability Assess",
            "value": risk
          },
        ];
        this.single2 = [...this.single2];
      }
    );
  }

  public getFundHistory(code: string) {
    this.chartService.getFundDetail$(code).subscribe(
      (data) => {
        console.log(data);
        this.history_data = data;

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

  public getBondHistory(code: string) {
    this.chartService.getBondDetail$(code).subscribe(
      (data) => {
        console.log(data);
        this.history_data = data;

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

  public getStockHistory(code: string) {
    console.log('code', code)
    this.chartService.getStockDetail$(code).subscribe(
      (data) => {
        console.log(data);
        this.history_data = data;

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
}
