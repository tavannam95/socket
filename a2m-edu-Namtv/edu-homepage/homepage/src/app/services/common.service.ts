import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

import {environment} from 'src/environments/environment';
import { RequestParam } from '../models/request-params';
import { ApiUrlUtil } from '../utils/api-url.util';
import { ParamUtil } from '../utils/param.util';


@Injectable(
  {  providedIn: 'root'}
)
export class CommonService {

  students: any[] = [
    {
      no: 1,
      name: 'Nguyễn Văn Thành',
      feeling: 'Môi trường học tập trên cả tuyệt vời, sau khóa học mình đã quyết định ở lại công ty làm việc tiếp khi được sự gợi ý của công ty.',
      rate: 5,
      img: 'assets/images/testimonial/thanhnv.png',
      location: 'Student'
    },
    {
      no: 2,
      name: 'Nguyễn Văn Hậu',
      feeling: 'Từ một sinh viên chưa có nhiều kinh nghiệp sau khi học xong khóa đào tạo của công ty, mình đã tự tin hơn để đi làm khi còn hơn 1 năm nữa mới ra trường',
      rate: 5,
      img: 'assets/images/testimonial/haunv.png',
      location: 'Student'
    },
    {
      no: 3,
      name: 'Thu Thương',
      feeling: 'Quyết định vào A2M Edu học lập trình là một quyết định sáng suốt nhất của tớ sau khi tốt nghiệp đại học. A2M Edu như ngôi nhà thứ hai, nơi tớ chiến thắng nỗi sợ lập trình. Các giảng viên không chỉ đáng yêu mà còn nhiệt huyết nữa.',
      rate: 5,
      img: 'assets/images/testimonial/thuthuong.png',
      location: 'Student'
    },
    {
      no: 4,
      name: 'Tiến Hợp',
      feeling: 'Đầu tiên phải cảm ơn các anh đã hướng dẫn mình trong khóa đào tạo, dù không làm việc ở công ty nữa nhưng mình đã học hỏi được rất nhiều để tự tin làm việc ở môi trường mới.',
      rate: 5,
      img: 'assets/images/testimonial/tienhop.png',
      location: 'Student'
    },
    {
      no: 5,
      name: 'Nông Ngọc',
      feeling: 'Nhờ sự chỉ dạy nhiệt tình từ các anh mà mình xóa bỏ suy nghĩ con gái không thể học lập trình và làm dev, không biết nói gì hơn ngoài cảm ơn A2m Edu đã giúp em tự tin hơn rất nhiều trên con đường mà em đã chọn',
      rate: 5,
      img: 'assets/images/testimonial/nongngoc.png',
      location: 'Student'
    },
    {
      no: 6,
      name: 'Nguyễn Trung Anh',
      feeling: 'Mình là người nhỏ tuổi nhất trong khóa đào tạo, có khá nhiều sự non nớt nhưng nhờ các anh chỉ dẫn mà mình có thể đáp ứng được yêu cầu của công việc, và gắn bó với công ty với vai trò là nhân viên part time.',
      rate: 5,
      img: 'assets/images/testimonial/anhnt.png',
      location: 'Student'
    },
    {
      no: 7,
      name: 'Thùy Dương',
      feeling: 'Con gái làm developer tại sao không nhỉ, A2M Edu giúp em những kiến thức nền tảng những bài thực hành sát với thực tế công việc, và quan trọng nhất nhờ các anh em tin em có thể và đã trở thành một developer',
      rate: 5,
      img: 'assets/images/testimonial/thuyduong.png',
      location: 'Student'
    },
    {
      no: 8,
      name: 'Nguyễn Chung Phong',
      feeling: 'Được sự chỉ dẫn của các anh giúp mình định hướng được rõ hơn các kĩ năng, kiến thức thứ mà trên trường mình không có được',
      rate: 5,
      img: 'assets/images/testimonial/phongnc.png',
      location: 'Student'
    },
    {
      no: 9,
      name: 'Ngọc Hà',
      feeling: 'Các anh siêu nhiệt tình và tận tâm, mình chuyển ngành nên kiến thức cơ bản rất yếu nhưng sau khóa học mình đã có thể xin được công việc',
      rate: 5,
      img: 'assets/images/testimonial/ngocha.png',
      location: 'Student'
    },


  ];
  a2mCourses: any[] = [
    {
      key: 0,
      courseNm: 'AI / Machine Learning ',
      courseStatus: 'Coming soon',
      courseGoal: 'Coming soon',
      courseContent: '',
      imgPath: 'assets/images/a2m/Machine-Learning.jpg',
      time: 6,
      rate: 5,
      numRate: 70,
      status: false
    },
    {
      key: 1,
      courseNm: 'Java Web Fullstack',
      courseStatus: 'Beginner',
      courseGoal: 'Công nghệ được đào tạo là Java Web cùng các kỹ năng cần thiết cho công việc phát triển phần mềm. '
                  + 'Người học từ mức chưa có hoặc chưa vững về kỹ thuật lập trình có thể nắm được cách tư duy trong công việc,'
                  + ' có thể bắt tay ngay vào công việc thực tế sau khóa học. Không những vậy, với những nền tảng có được, '
                  + 'người học còn có khả năng tự học và phát triển bản thân và nghề nghiệp lâu dài.',
      courseContent: '',
      imgPath: 'assets/images/a2m/img_java.jpg',
      time: 6,
      rate: 5,
      numRate: 70,
      status: true
    },

    {
      key: 0,
      courseNm: 'Lập trình di động đa nền tảng',
      courseStatus: 'Coming soon',
      courseGoal: 'Coming soon',
      courseContent: '',
      imgPath: 'assets/images/a2m/img_flutter.png',
      time: 3,
      rate: 5,
      numRate: 70,
      status: false
    },

  ];

  courses: any[] = [
    {
      courseId: 1,
      courseNm: 'Java Web, Spring MVC, Spring boot',
      rate: 5,
      numRate: 70,
      courseGoal: 'Beginner',
      courseContent: 'Lorem ipsum dolor sit amet consectur adpis elit sed eiusmod tempor incididunt labore dolore magna aliquaenim.',
      lessons: 8,
      time: 6,
      img: 'assets/images/course/course-01.jpg'
    },
    {
      courseId: 2,
      courseNm: 'Java Web, Spring MVC, Spring boot',
      rate: 5,
      numRate: 70,
      courseGoal: 'Beginner',
      courseContent: 'Lorem ipsum dolor sit amet consectur adpis elit sed eiusmod tempor incididunt labore dolore magna aliquaenim.',
      lessons: 8,
      time: 6,
      img: 'assets/images/course/course-01.jpg'
    },
    {
      courseId: 3,
      courseNm: 'Java Web, Spring MVC, Spring boot',
      rate: 5,
      numRate: 70,
      courseGoal: 'Beginner',
      courseContent: 'Lorem ipsum dolor sit amet consectur adpis elit sed eiusmod tempor incididunt labore dolore magna aliquaenim.',
      lessons: 8,
      time: 6,
      img: 'assets/images/course/course-01.jpg'
    },

  ];
  teachers: any[] = [
    {
      id: 0,
      position: ' Professional Trainer',
      name: 'Nguyễn Thị Hải Yến',
      shortDescription: '',
      description: 'NGHIỆP ĐẠO THÙ TINH'
      + '\n ***'
      + '\n Trong bất cứ ngành nghề nào, lĩnh vực nào, cái quan trọng nhất là sự am hiểu, tinh thông thứ mình đang làm, được như vậy thì chắc chắn sẽ được sự công nhận và thành quả xứng đáng. Con đường nghề trợ giúp cho người giỏi giang, có câu "nhất nghệ tinh, nhất thân vinh" cũng chung cái ý đó. '
      + '\n Làm thế nào để tinh? Không gì ngoài chữ "cần". "Nghệ tinh ư cần" - tay nghề tinh thuần là nhờ cần cù luyện rèn, không ngừng học tập mở mang. Lại có câu "thiên đạo thù cần" - trời giúp kẻ luôn luôn nỗ lực. Thế thì cần cù, chăm chỉ là chìa khóa thành công của bất cứ công việc nào, người cần cù luôn được trợ giúp từ nhiều phía.',
      address: 'Hà Đông, Hà Nội',
      email: 'quangnd@atwom.com.vn',
      phone: '+84 (0) 888 6886 88',
      img: 'assets/images/a2m/LTHYEN.jpg'
    },
    {
      id: 1,
      position: ' M.E',
      name: 'Nguyễn Đình Quảng',
      shortDescription: '',
      description: 'NGHIỆP ĐẠO THÙ TINH'
      + '\n ***'
      + '\n Trong bất cứ ngành nghề nào, lĩnh vực nào, cái quan trọng nhất là sự am hiểu, tinh thông thứ mình đang làm, được như vậy thì chắc chắn sẽ được sự công nhận và thành quả xứng đáng. Con đường nghề trợ giúp cho người giỏi giang, có câu "nhất nghệ tinh, nhất thân vinh" cũng chung cái ý đó. '
      + '\n Làm thế nào để tinh? Không gì ngoài chữ "cần". "Nghệ tinh ư cần" - tay nghề tinh thuần là nhờ cần cù luyện rèn, không ngừng học tập mở mang. Lại có câu "thiên đạo thù cần" - trời giúp kẻ luôn luôn nỗ lực. Thế thì cần cù, chăm chỉ là chìa khóa thành công của bất cứ công việc nào, người cần cù luôn được trợ giúp từ nhiều phía.',
      address: 'Hà Đông, Hà Nội',
      email: 'quangnd@atwom.com.vn',
      phone: '+84 (0) 888 6886 88',
      img: 'assets/images/a2m/NDQUANG.jpg'
    },
    {
      id: 2,
      position: 'CTO',
      name: 'Đoàn Trần Lệ',
      shortDescription: '',
      description: 'NGHIỆP ĐẠO THÙ TINH'
      + '\n ***'
      + '\n Trong bất cứ ngành nghề nào, lĩnh vực nào, cái quan trọng nhất là sự am hiểu, tinh thông thứ mình đang làm, được như vậy thì chắc chắn sẽ được sự công nhận và thành quả xứng đáng. Con đường nghề trợ giúp cho người giỏi giang, có câu "nhất nghệ tinh, nhất thân vinh" cũng chung cái ý đó. '
      + '\n Làm thế nào để tinh? Không gì ngoài chữ "cần". "Nghệ tinh ư cần" - tay nghề tinh thuần là nhờ cần cù luyện rèn, không ngừng học tập mở mang. Lại có câu "thiên đạo thù cần" - trời giúp kẻ luôn luôn nỗ lực. Thế thì cần cù, chăm chỉ là chìa khóa thành công của bất cứ công việc nào, người cần cù luôn được trợ giúp từ nhiều phía.',
      address: 'Hà Đông, Hà Nội',
      email: 'quangnd@atwom.com.vn',
      phone: '+84 (0) 888 6886 88',
      img: 'assets/images/a2m/DTLE.png'
    },
    {
      id: 3,
      position: 'Senior  developer',
      name: 'Phạm Việt Anh',
      shortDescription: '',
      description: 'NGHIỆP ĐẠO THÙ TINH'
      + '\n ***'
      + '\n Trong bất cứ ngành nghề nào, lĩnh vực nào, cái quan trọng nhất là sự am hiểu, tinh thông thứ mình đang làm, được như vậy thì chắc chắn sẽ được sự công nhận và thành quả xứng đáng. Con đường nghề trợ giúp cho người giỏi giang, có câu "nhất nghệ tinh, nhất thân vinh" cũng chung cái ý đó. '
      + '\n Làm thế nào để tinh? Không gì ngoài chữ "cần". "Nghệ tinh ư cần" - tay nghề tinh thuần là nhờ cần cù luyện rèn, không ngừng học tập mở mang. Lại có câu "thiên đạo thù cần" - trời giúp kẻ luôn luôn nỗ lực. Thế thì cần cù, chăm chỉ là chìa khóa thành công của bất cứ công việc nào, người cần cù luôn được trợ giúp từ nhiều phía.',
      address: 'Hà Đông, Hà Nội',
      email: 'quangnd@atwom.com.vn',
      phone: '+84 (0) 888 6886 88',
      img: 'assets/images/a2m/img_va.png'
    },
    {
      id: 4,
      position: 'Senior  developer',
      name: 'Hoàng Quốc Đoàn',
      shortDescription: '',
      description: 'NGHIỆP ĐẠO THÙ TINH'
      + '\n ***'
      + '\n Trong bất cứ ngành nghề nào, lĩnh vực nào, cái quan trọng nhất là sự am hiểu, tinh thông thứ mình đang làm, được như vậy thì chắc chắn sẽ được sự công nhận và thành quả xứng đáng. Con đường nghề trợ giúp cho người giỏi giang, có câu "nhất nghệ tinh, nhất thân vinh" cũng chung cái ý đó. '
      + '\n Làm thế nào để tinh? Không gì ngoài chữ "cần". "Nghệ tinh ư cần" - tay nghề tinh thuần là nhờ cần cù luyện rèn, không ngừng học tập mở mang. Lại có câu "thiên đạo thù cần" - trời giúp kẻ luôn luôn nỗ lực. Thế thì cần cù, chăm chỉ là chìa khóa thành công của bất cứ công việc nào, người cần cù luôn được trợ giúp từ nhiều phía.',
      address: 'Hà Đông, Hà Nội',
      email: 'quangnd@atwom.com.vn',
      phone: '+84 (0) 888 6886 88',
      img: 'assets/images/a2m/HQDOAN.png'
    },

  ];
  categories: any[] = [
    {
      name : 'Java - Spring',
      url: '#'
    },
    {
      name : 'Korean',
      url: '#'
    }
  ]
  faqList: any[] = [
    {
      id: 1,
      question: 'Học xong có làm được việc ngay không?',
      answer: 'Các khóa học sẽ cung cấp cho bạn đủ nền tảng kiến thức, kỹ năng cũng như thái độ làm việc đúng đắn để không những có thể làm được việc ngay khi tốt nghiệp, bạn còn có thể tự học thêm để mở rộng cơ hội thăng tiến trong nghề nghiệp sau này.'
    },
    {
      id: 2,
      question: 'Học xong có tìm được việc luôn không?',
      answer: 'Bản thân công ty AtwoM Việt Nam và các đối tác của chúng tôi vẫn đang có nhu cầu rất lớn về nhân sự. Chỉ cần bạn tham gia nghiêm túc khóa học và hoàn thành các giáo án công ty sẽ đảm bảo cho bạn một việc làm ổn định với mức thu nhập xứng đáng với năng lực của mình.'
    },
    {
      id: 3,
      question: 'Nội dung các khóa học có đảm bảo chất lượng không?',
      answer: 'Khác với những nội dung mang tính hàn lâm trong các trường đại học, cao đẳng, AtwoMEdu tập trung vào các nội dung mang tính thực tiễn để giúp bạn đáp ứng được những yêu cầu công việc phổ dụng nhất hiện nay. Nội dung khóa học sẽ luôn được cập nhật và điều chỉnh cho phù hợp với sự thay đổi của công nghệ thế giới.'
    },
    {
      id: 4,
      question: 'Chất lượng đào tạo của Trung tâm có đảm bảo không?',
      answer: 'Với nhiều năm hoạt động ở Việt Nam, AtwoM Việt Nam đã có nhiều kinh nghiệm trong việc đào tạo lập trình viên, các học viên bước ra từ chương trình đều có công việc ổn định với mức đãi ngộ rất xứng đáng. Chúng tôi đào tạo ra nhân tài cho chính công ty lẫn các đối tác tin cậy của mình, chất lượng đầu ra luôn là yếu tố hàng đầu. Chúng tôi sẽ là người đồng hành, không chỉ trong mà còn cả sau khoa học.'
    },
    {
      id: 5,
      question: 'Tôi cần điều kiện gì để tham gia?',
      answer: 'Bạn không cần biết về lập trình để tham gia khóa học. Bạn chỉ cần có khả năng tư duy rành mạch và tâm lý sẵn sàng để tham gia khóa học một cách nghiêm túc.'
    },

  ];

  constructor(
    private http: HttpClient,
    ) {
  }

  getCoursesList(): any[] {
    // return this.courses;
    return this.a2mCourses;
  }
  // include feedback
  getStudentsList(): any[] {
    return this.students;
  }


  getTeachersList() {
    return this.teachers;
  }

  getTeacherInfo(number: number) {
    return this.teachers[number];
  }

  getNews(filter?:any): Observable<object> {
    const params: RequestParam[] = ParamUtil.toRequestParams(filter ?? {});
    const url = ApiUrlUtil.buildQueryString(environment.apiHost + '/homepage/news', params);
    return this.http.get(url, {  });
  }

  getById(id:number): Observable<object> {
    const url = environment.apiHost + '/homepage/getById?id=' + id;
    return this.http.get(url, {  });
  }

  getCategories() {
    return this.categories;
  }

  getFAQs() {
    return this.faqList;
  }

  // getSpinnerDialog(){
  //   const dialogRef = this.dialog.open(SpinnerDialogComponent, {
  //     panelClass: "alertModal",
  //     disableClose: true
  //   });

  //   return dialogRef;
  // }
}
