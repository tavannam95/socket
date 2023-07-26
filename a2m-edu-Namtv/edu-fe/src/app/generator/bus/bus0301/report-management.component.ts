import { LangUtil } from 'src/app/utils/lang.util';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { faServer } from '@fortawesome/free-solid-svg-icons';
import { Bus0301Service } from 'src/app/services/bus/bus0301.service';
import { Subscription } from 'rxjs';
import { FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-report-management',
  templateUrl: './report-management.component.html',
  styleUrls: ['./report-management.component.css'],
})
export class ReportManagementComponent implements OnInit, AfterViewInit {
  endDate: any;
  faServer = faServer;
  today = new Date();
  month = this.today.getMonth();
  year = this.today.getFullYear();
  basicData: any;
  basicData2: any;
  recordList = [];
  request: any = {};
  statusList = [
    { label: 'All', value: null },
    { label: 'Active', value: 'Y' },
    { label: 'Block', value: 'N' },
  ];

  basicOptions: any = {
    plugins: {
      legend: {
        display: false,
      },
    },
    maintainAspectRatio: false,
  };

  subscription: Subscription | undefined;

  authS: number = 0;
  genModelS: number = 0;
  genDataS: number = 0;
  DeliveryS: number = 0;

  constructor(
    private bus0301Service: Bus0301Service,
    public langUtil: LangUtil
  ) {}

  ngAfterViewInit(): void {
    this.onSearch();
  }

  ngOnInit(): void {
    this.request.startDate =
      new Date(this.year, this.month, 1);

    this.request.EndDate = new Date(this.year, this.month, 28);
  }

  onSearch() {
    var param = {
      FROM_DT:
        this.request.startDate != null
          ? this.formatDate(this.request.startDate)
          : '',
      TO_DT:
        this.request.EndDate != null
          ? this.formatDate(this.request.EndDate)
          : '',
    };
    // console.log(param);

    this.bus0301Service.getListCustomer(param).subscribe((data) => {
      this.recordList = data;
      let listMonth = data.map((item: { START_MONTH: any }) => {
        return item.START_MONTH;
      });
      let regisNum = data.map((item: { TOTAL_REGIS: any }) => {
        return item.TOTAL_REGIS;
      });
      let totalPrice = data.map((item: { TOTAL_PRICE: any }) => {
        return item.TOTAL_PRICE;
      });

      this.basicData = {
        labels: listMonth,
        datasets: [
          {
            label: 'Second Dataset',
            data: regisNum,
            fill: false,
            borderColor: '#FFA726',
            tension: 0.4,
          },
        ],
      };
      this.basicData2 = {
        labels: listMonth,
        datasets: [
          {
            label: 'Second Dataset',
            data: totalPrice,
            fill: false,
            borderColor: '#FFA726',
            tension: 0.4,
          },
        ],
      };
    });
  }
  onStartChange(event: any) {}

  checkAvailability() {}
  formatDate(date: any) {
    return (
      [
        date.getFullYear(),
        this.padTo2Digits(date.getMonth() + 1),
        this.padTo2Digits(date.getDate()),
      ].join('-') +
      ' ' +
      [
        this.padTo2Digits(date.getHours()),
        this.padTo2Digits(date.getMinutes()),
        this.padTo2Digits(date.getSeconds()),
      ].join(':')
    );
  }
  onReset(){
    this.request.startDate =
      new Date(this.year, this.month, 1);

    this.request.EndDate = new Date(this.year, this.month, 28);
    this.onSearch();
  }
  padTo2Digits(num: number) {
    return num.toString().padStart(2, '0');
  }

  updateChartOptions() {
    this.applyLightTheme();
  }

  applyLightTheme() {
    this.basicOptions = {
      responsive: false,
      maintainAspectRatio: false,
      plugins: {
        legend: {
          display: false,

          // labels: {
          //   color: '#495057'
          // }
        },
      },
      scales: {
        x: {
          ticks: {
            color: '#495057',
          },
          grid: {
            color: '#ebedef',
          },
        },
        y: {
          ticks: {
            color: '#495057',
          },
          grid: {
            color: '#ebedef',
          },
        },
      },
    };
  }

  ngOnDestroy() {}
}
