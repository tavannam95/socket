import { AfterViewInit, Component, ElementRef, Input, OnChanges, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { GetpdfService } from 'src/app/services/file/pdf.service';

@Component({
  selector: 'app-document-view',
  templateUrl: './document-view.component.html',
  styleUrls: ['./document-view.component.css']
})
export class DocumentViewComponent implements OnInit, AfterViewInit, OnChanges {
  @ViewChild('pdfViewer')
  pdfViewer!: ElementRef;

  @Input() filePath: string = "";
  @Input() fileType: string = "pdf";
  @Input() display: string = "";
  constructor(
    private pdfService: GetpdfService,
  ) { }
  ngOnInit(): void {

  }

  ngAfterViewInit(): void {
    
  }

  ngOnChanges(): void {
    if(this.filePath != ""){
      this.getFile();
    }
    
  }

  getFile() {
    if (this.fileType == "pdf") {
      this.pdfService.getPdf(this.filePath, this.fileType).subscribe((responseMessage) => {
        let file = new Blob([responseMessage], { type: 'application/pdf' });
        var fileURL = URL.createObjectURL(file);
        this.pdfViewer.nativeElement.data = fileURL;
      })
    } else {

      this.pdfService.getExcel(this.filePath, this.fileType).subscribe((responseMessage) => {
        let file = new Blob([responseMessage], { type: 'application/vnd.ms-excel' });
        var fileURL = URL.createObjectURL(file);
        window.open(fileURL, "_blank");
      })
    }
  }

  getFileInNewWindow() {
    if (this.fileType == "pdf") {
      this.pdfService.getPdf(this.filePath, this.fileType).subscribe((responseMessage) => {
        let file = new Blob([responseMessage], { type: 'application/pdf' });
        var fileURL = URL.createObjectURL(file);
        window.open(fileURL);
      })
    } else {
      this.pdfService.getExcel(this.filePath, this.fileType).subscribe((responseMessage) => {
        let file = new Blob([responseMessage], { type: 'application/vnd.ms-excel' });
        var fileURL = URL.createObjectURL(file);
        window.open(fileURL);
      })
    }
  }
}
