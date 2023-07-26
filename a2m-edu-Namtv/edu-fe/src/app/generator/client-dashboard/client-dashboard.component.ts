import {Component, OnDestroy, OnInit} from '@angular/core';
import { faServer } from '@fortawesome/free-solid-svg-icons';
import {Subscription} from "rxjs";


@Component({
  selector: 'app-dashboard',
  templateUrl: './client-dashboard.component.html',
  styleUrls: ['./client-dashboard.component.css']
})
export class ClientDashboardComponent implements OnInit, OnDestroy {
  faServer = faServer;

  basicData: any;
  basicData2: any;


  basicOptions: any = {
    plugins: {
      legend: {
        display: false
      }
    },
    maintainAspectRatio: false};

  subscription: Subscription | undefined;


  constructor() { }

  ngOnInit(): void {
    this.basicData = {
      labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
      datasets: [
        {
          label: 'Second Dataset',
          data: [28, 48, 40, 19, 86, 27, 90],
          fill: false,
          borderColor: '#FFA726',
          tension: .4
        }
      ]
    };

    this.basicData2 = {
      labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
      datasets: [
        {
          label: 'First Dataset',
          data: [6500, 5900, 8000, 8100, 9600, null, null],
          fill: false,
          borderColor: '#42A5F5',
          tension: .4
        },
        {
          label: 'First Dataset',
          data: [null, null, null, null, 9600, 5500, 7000],
          fill: false,
          borderColor: '#ad42f5',
          tension: .4
        },

      ]
    };
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
          display: false

          // labels: {
          //   color: '#495057'
          // }
        }
      },
      scales: {
        x: {
          ticks: {
            color: '#495057'
          },
          grid: {
            color: '#ebedef'
          }
        },
        y: {
          ticks: {
            color: '#495057'
          },
          grid: {
            color: '#ebedef'
          }
        }
      }
    };
  }

  ngOnDestroy() {
  }

}
