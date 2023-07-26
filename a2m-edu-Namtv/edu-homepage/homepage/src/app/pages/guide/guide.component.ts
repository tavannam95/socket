import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-guide',
  templateUrl: './guide.component.html'
})
export class GuideComponent implements OnInit {

  guides : any[] = [] ;

  faqs: any[] = [] ;

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.guides=[
      {title : 'Bước 1: Đăng ký tư vấn và xét tuyển ',
      content :'Bạn có thể liên hệ AtwoM Việt Nam để nhận được tư vấn và đăng ký xét tuyển bằng một trong các cách sau'+':'
      +  '– Đăng ký trực tiếp ngay tại đây'
      +  '– Gọi đến số hotline: 02462538829'
      +  '– Inbox qua Facebook: https://www.facebook.com/a2mvn'
      +  '– Gửi email đến hanoi@AtwoM Việt Nam.vn'
      },
      {title : 'Bước 2: Nhận lịch hẹn xét tuyển',
      content :'Sau khi nhận được đăng ký xét tuyển và tư vấn cho bạn khoá học phù hợp, bộ phận Tuyển sinh của AtwoM Việt Nam sẽ liên hệ đặt lịch hẹn mời bạn đến tham dự phiên xét tuyển tại các cơ sở đào tạo của AtwoM Việt Nam.'},
      {title : 'Bước 3: Thi tuyển và phỏng vấn ',
      content :
      'Tại phiên xét tuyển, bạn sẽ trải qua các vòng sau:'

      +'– Vòng làm bài thi đánh giá năng lực: Có 2 loại bài thi đánh giá năng lực dành riêng cho 2 đối tượng khác nhau. Bài thi đánh giá năng lực không chỉ là để làm căn cứ xét tuyển mà còn là để giáo viên giảng dạy có thêm thông tin về học viên để xếp lớp và hỗ trợ phù hợp trong quá trình học.'
      +'Bạn là người mới bắt đầu, chưa có nền tảng lập trình: Bạn cần trải qua 1 bài thi IQ/GMAT, bao gồm các câu hỏi toán học và tư duy cơ bản.'
      +'Bạn đã có nền tảng lập trình: Bạn cần trải qua 1 bài thi Lập trình căn bản bao gồm các nội dung như: câu lệnh điều kiện, câu lệnh lặp, hàm, các thuật toán đơn giản.'
      +'Vòng phỏng vấn chuyên môn với các nội dung:'
      +'Khả năng tiếp thu, tư duy, khả năng giao tiếp của ứng viên.'
      +'Mong muốn của ứng viên với việc làm trong ngành IT, đánh giá động lực và khả năng theo đuổi quá trình học tập cường độ cao tại AtwoM Việt Nam.'
      +'Giải đáp các câu hỏi liên quan đến ngành nghề IT.'
      +'Giải đáp các câu hỏi liên quan đến chương trình đào tạo của AtwoM Việt Nam.'
      +'Cả 2 vòng đều diễn ra cùng ngày bạn đến tham dự xét tuyển.'

    },
      {title : 'Bước 4: Nhận kết quả xét tuyển',
      content :'Sau khi hoàn thành bài xét tuyển, bộ phận Tuyển sinh sẽ thông báo với bạn kết quả xét tuyển và hướng dẫn bạn các thủ tục đăng ký nhập học nếu trúng tuyển.'},
      {title : 'Bước 5: Đăng ký nhập học và nộp học phí',
      content :'Bạn sẽ được mời đến các cơ sở đào tạo của AtwoM Việt Nam để nộp Đơn đăng ký nhập học và bắt đầu khóa học.'},
    ]
  }

  goToRegis(){
    // let a = document.createElement('a');
    // a.href = '/apply';
    // a.click();

    this.router.navigate(['/apply'], {
      queryParams: {},
      // state: { searchRequest: $.extend(true, {}, this.request) },
    });
  }

}
