import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Course0102Service } from 'src/app/services/course/course0102.service';
import { FileUploadService } from 'src/app/services/file/file-upload.service';

@Component({
  selector: 'app-subject-schedule',
  templateUrl: './subject-schedule.component.html',
  styleUrls: ['./subject-schedule.component.css']
})
export class SubjectScheduleComponent implements OnInit {

  subjectId: string = "";
  courseId: string = "";
  pdfFilePath : any = {};
  constructor(
    private course0102Service: Course0102Service,
    private route : ActivatedRoute,
    private fileUploadService :FileUploadService,
  ) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(async (params) => {
      this.subjectId = params['subjectId']?params['subjectId']:"";
      this.courseId = params['courseId']?params['courseId']:"";
    });
  }

  exportPDF(){
    const subjectId  = Number(this.subjectId)
    this.course0102Service.exportPDFLectureSchedule(subjectId).toPromise().then((response)=>{
      const myArray = response.detail.split("!#@");
      this.pdfFilePath.fleNm = myArray[1];
      this.pdfFilePath.fleTp = '.pdf';
      this.pdfFilePath.flePath = myArray[0] + this.pdfFilePath.fleTp;
      this.fileUploadService.download(this.pdfFilePath, true);
    })
  }

}
