import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ApexChart, ApexNonAxisChartSeries, ApexResponsive, ChartComponent } from 'ng-apexcharts';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { CommonConstant } from 'src/app/constants/common.constant';
import { CommonService } from 'src/app/services/common/common.service';
import { QuizService } from 'src/app/services/course/quiz.service';
import { Edu0201Service } from 'src/app/services/edu/edu0201.service';
import { IqTestService } from 'src/app/services/quesIq/iqTest.service';
import { CommonUtil } from 'src/app/utils/common-util';

export type ChartOptions = {
  series: ApexNonAxisChartSeries;
  chart: ApexChart;
  responsive: ApexResponsive[];
  labels: any;
};
@Component({
  selector: 'app-iqTeststatistical-result-question',
  templateUrl: './iqTeststatistical-result-question.component.html',
  styleUrls: ['./iqTeststatistical-result-question.component.css']
})
export class IqTestStatisticalResultQuestionComponent implements OnInit {

  @ViewChild("chart") chart: ChartComponent | undefined;
  public chartOptions!: Partial<ChartOptions> | any;
  chartOptions2 = {
    title:{
      text: ""
    },
    animationEnabled: true,
    data: [{
      type: "column",
      dataPoints: [
        { y: 300878, label: "Venezuela" },
        { y: 266455,  label: "Saudi" },
        { y: 169709,  label: "Canada" },
      ]
    }]
  }

  basicData = {
    labels: ['70% - 100%', '50% - 70%', '0% - 50%'],
    datasets: [
        {
            label: '70% - 100%',
            backgroundColor: [
              '#EC407A',
              '#AB47BC',
              '#42A5F5'
          ]
        },
        {
          label: '50% - 70%',
          backgroundColor: [
            '#EC407A',
            '#AB47BC',
            '#42A5F5'
          ],
          data: [65, 59, 80]
        },
        {
          label: '0% - 50%',
          backgroundColor: [
            '#EC407A',
            '#AB47BC',
            '#42A5F5'
        ]
      }
    ]
  };

  multiAxisOptions: any ={}


  pageRequest: any = {};
  chartData: any = "";
  iqTestId: any = NaN;
  listQuestion: any[] = [];
  totalRecords: any = 10;
  totalStudentSubmit: number = 0;
  constructor(
    private route : ActivatedRoute,
    private iqTestService: IqTestService,
    private toastrService: ToastrService,
    public dialog: MatDialog,
    private commonService: CommonService,
    private edu0201Service: Edu0201Service,
    private trans: TranslateService,
    private confirmationService: ConfirmationService,
  ) {
    this.chartOptions = {
      series: [1, 1, 1],
      chart: {
        width: 600,
        type: "pie"
      },
      labels: ["70% - 100%", "50% - 70%", "0% - 50%"],
      responsive: [
        {
          breakpoint: 480,
          options: {
            chart: {
              width: 200
            },
            legend: {
              position: "bottom"
            }
          }
        }
      ]
    };
  }
  ngOnInit(): void {
    this.route.queryParams.subscribe(async (params) => {
      this.iqTestId = params['iqTestId']?params['iqTestId']:"";
    });
    this.initData();
  }

  initData(){
    this.initDataStatic();
    this.initDataFlexible();
  }

  initDataStatic(){
    this.initChartOption();
    this.getDataChart();
  }

  initChartOption(){

  }

  getDataChart(){
    let param = Object.assign( {
      fullName : "",
      classIds : "",
      iqTestId : this.iqTestId
    })
    // this.iqTestService.getStatisResult(param).subscribe((response) => {
    //   //
    //   this.chartData = response[CommonConstant.RESULT_KEY];
    //   this.migrateDataChart();
    // });
  }

  migrateDataChart(){
    if(this.chartData.nonQuiz) return;

    this.chartOptions.series = [
      this.chartData.professional, this.chartData.average, this.chartData.noob
    ]

    this.chartOptions2 = {
      title:{
        text: ""
      },
      animationEnabled: true,
      data: [{
        type: "column",
        dataPoints: [
          { y: this.chartData.professional, label: "70% - 100%" },
          { y: this.chartData.average,  label: "50% - 70%" },
          { y:  this.chartData.noob,  label: "0% - 50%" },
        ]
      }]
    }

    this.totalStudentSubmit = this.chartData.professional + this.chartData.average + this.chartData.noob;

    this.basicData.datasets[1].data = [
      this.chartData.professional, this.chartData.average, this.chartData.noob
    ]

  }

  initDataFlexible(){
    this.initPaging();
    this.onSearch();
  }

  initPaging(){
    this.pageRequest.page = 0;
    this.pageRequest.rows = CommonConstant.PAGE_SIZE;
  }

  onSearch(){
    this.getListQuestion();
  }

  getPaging(event: any) {
    this.pageRequest.page = event.page;
    this.pageRequest.rows = event.rows;
    this.onSearch();
  }

  getListQuestion(){
    this.pageRequest.iqTestId = this.iqTestId;
    this.iqTestService.getListStatisticalByQuestion(this.pageRequest).subscribe((response) => {
      this.listQuestion = response[CommonConstant.LIST_KEY];
      this.totalRecords = response[CommonConstant.COUNT_ITEMS_KEY];

      CommonUtil.addIndexForListReverse(this.listQuestion, this.pageRequest.page, this.totalRecords);
    });
  }

}
