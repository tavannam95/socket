package com.a2m.gen.services.common;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.a2m.gen.entities.TccoStd;
import com.a2m.gen.entities.TsstUserInfo;
import com.a2m.gen.models.TsstUserInfoModel;
import com.a2m.gen.models.course.LectureModel;
import com.a2m.gen.models.course.SbjChapterModel;
import com.a2m.gen.models.course.SubjectModel;
import com.a2m.gen.models.course.SubjectStandardModel;
import com.a2m.gen.models.course.SubjectStandardNoteModel;
import com.a2m.gen.models.edu.TeacherModel;
import com.a2m.gen.services.course.QuizService;
import com.itextpdf.layout.Document;

@Service

public class ExportToWordService {

    @Autowired
    QuizService quizService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private TccoSTDService tccoSTDService;

    @Autowired
    ResourceLoader resourceLoader;

    @Value("${path.upload.dir}")
    private String pathUploadDir;

    @Value("${path.font.times.dir}")
    private String pathFontTimeDir;

    public void addTableScheduleBySubject(XWPFDocument doc, List<HashMap<String, Object>> list) {
        List<TccoStd> listTcco = tccoSTDService.getCommCdByUpCommCd("12");
        // Creating a table
        String s = "5-30-10";
        for (TccoStd tcco : listTcco) {
            s += "-10";
        }
        String[] arr = s.split("-");
        float[] pointColumnWidths = new float[arr.length];
        for (int count = 0; count < pointColumnWidths.length; count++) {
            pointColumnWidths[count] = Float.parseFloat(arr[count]);
        }

        XWPFTable table = doc.createTable();
        XWPFTableRow header = table.getRow(0);
        header.getCell(0).setText("STT.");
        header.getCell(0).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
        header.addNewTableCell().setText("Nội dung");
        header.getCell(1).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
        table.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(500));
        table.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2500));

        List<Long> lastRows = new ArrayList<Long>();
        for (int i = 0; i < listTcco.size(); i++) {
            header.addNewTableCell().setText(listTcco.get(i).getCommNmVi());
            header.getCell(i + 2).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
            table.getRow(0).getCell(i + 2).getCTTc().addNewTcPr().addNewTcW()
                    .setW(BigInteger.valueOf((10000 - 500 - 2500 - 1000) / listTcco.size()));
            lastRows.add(Long.valueOf(0));
        }
        header.addNewTableCell().setText("Tổng");

        int index = 1;

        Long totalALL = Long.valueOf(0);
        for (HashMap<String, Object> item : list) {

            XWPFTableRow row = table.createRow();
            row.getCell(0).setText(String.valueOf(index++));
            row.getCell(0).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
            row.getCell(1).setText(String.valueOf((String) item.get("chapterNm")));
//            row.getCell(1).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
            HashMap<String, Object> organization = (HashMap<String, Object>) item.get("organizationFormals");
            Long total = Long.valueOf(0);
            int indexTcco = 0;
            for (int i = 0; i < listTcco.size(); i++) {
                Long value = (Long) organization.get(listTcco.get(i).getCommCd() + "");
                total += value;

                row.getCell(i + 2).setText(String.valueOf(String.valueOf(value)));
                row.getCell(i + 2).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);

                lastRows.set(indexTcco, lastRows.get(indexTcco) + value);
                indexTcco++;
            }
            totalALL += total;
            row.getCell(listTcco.size() + 2).setText(String.valueOf(String.valueOf(total)));
            row.getCell(listTcco.size() + 2).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);

        }

        // footer
        XWPFTableRow row = table.createRow();
        row.getCell(0).setText(String.valueOf(""));
        row.getCell(0).setVerticalAlignment(XWPFVertAlign.CENTER);

        row.getCell(1).setText(String.valueOf("Tổng cộng"));
        row.getCell(1).setVerticalAlignment(XWPFVertAlign.CENTER);

        for (int i = 0; i < lastRows.size(); i++) {
            row.getCell(i + 2).setText(String.valueOf(String.valueOf(lastRows.get(i))));
            row.getCell(i + 2).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
        }

        row.getCell(lastRows.size() + 2).setText(String.valueOf(String.valueOf(totalALL)));
        row.getCell(lastRows.size() + 2).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
        XWPFParagraph p1 = doc.createParagraph();
        p1.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun stand = p1.createRun();
        stand.setFontSize(12);
    }

    public void addTableStandardBySubject(XWPFDocument doc, SubjectModel model) {
        List<SubjectStandardModel> listStand = model.getListStand();
        List<SbjChapterModel> listChapter = model.getChapterModels();
        List<SubjectStandardModel> listKnow = new ArrayList<SubjectStandardModel>();
        List<SubjectStandardModel> listSkill = new ArrayList<SubjectStandardModel>();
        List<SubjectStandardModel> listQua = new ArrayList<SubjectStandardModel>();
        for (SubjectStandardModel stand : listStand) {
            String standType = stand.getStandType();
            if (standType.equals("18-01")) {
                listKnow.add(stand);
            } else if (standType.equals("18-02")) {
                listSkill.add(stand);
            } else {
                listQua.add(stand);
            }
        }

        XWPFParagraph p1 = doc.createParagraph();
        p1.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun stand = p1.createRun();
        stand.setFontSize(12);

        stand.setText("1) Về kiến thức: ");
        stand.addBreak();
        int idx = 1;
        for (int i = 0; i < listKnow.size(); i++) {
            stand.setText("CLO" + String.valueOf(idx++) + ": " + listKnow.get(i).getStandContent());
            stand.addBreak();
            if (i == listKnow.size() - 1) {
                stand.addBreak();
            }
        }

        stand.setText("2) Về kỹ năng: ");
        stand.addBreak();
        for (int i = 0; i < listSkill.size(); i++) {
            stand.setText("CLO" + String.valueOf(idx++) + ": " + listSkill.get(i).getStandContent());
            stand.addBreak();
            if (i == listSkill.size() - 1) {
                stand.addBreak();
            }
        }

        stand.setText("3) Về phẩm chất: ");
        stand.addBreak();
        for (int i = 0; i < listQua.size(); i++) {
            stand.setText("CLO" + String.valueOf(idx++) + ": " + listQua.get(i).getStandContent());
            stand.addBreak();
            if (i == listQua.size() - 1) {
                stand.addBreak();
            }
        }
        stand.setText("Ghi chú: CLO = Course Learning Outcomes = Chuẩn đầu ra của học phần.");
        stand.addBreak();

        String s = "5-15";
        float width = 0;
        if (listStand.size() != 0) {
            width = (100 - 5 - 15) / listStand.size();
        }
        for (SubjectStandardModel standard : listStand) {
            s += "-" + width + "";
        }

        String[] arr = s.split("-");
        float[] pointColumnWidths = new float[arr.length];
        for (int count = 0; count < pointColumnWidths.length; count++) {
            pointColumnWidths[count] = Float.parseFloat(arr[count]);
        }
        int widthStand = (int) (10000 - 500 - 2000) / listStand.size();

        XWPFTable table = doc.createTable();
        XWPFTableRow header1 = table.getRow(0);
        header1.getCell(0).setText("STT.");
        header1.getCell(0).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
        header1.addNewTableCell().setText("Nội dung");
        header1.getCell(1).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
        table.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(500));
        table.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2000));
        int idxClo = 1;
        for (int i = 0; i < listStand.size(); i++) {
            header1.addNewTableCell().setText(listStand.get(i).getStandCode() == null ? ""
                    : listStand.get(i).getStandCode() + " " + String.valueOf(idxClo++));
            header1.getCell(i + 2).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
            table.getRow(0).getCell(i + 2).getCTTc().addNewTcPr().addNewTcW()
                    .setW(BigInteger.valueOf(widthStand));
        }

//        
        int index = 1;
        for (SbjChapterModel item : listChapter) {
            XWPFTableRow row = table.createRow();
            row.getCell(0).setText(String.valueOf(index++));
            row.getCell(0).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
            row.getCell(1).setText(item.getChapterNm() == null ? "" : item.getChapterNm());
//            row.getCell(1).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);

            List<SubjectStandardNoteModel> listStandNote = item.getListStandNote();
            for (SubjectStandardNoteModel note : listStandNote) {
                for (SubjectStandardModel standard : listStand) {
                    if (note.getStandId() == standard.getKey()) {
                        standard.setStandResult(note.getStandResult());
                    }
                }
            }
            for (int i = 0; i < listStand.size(); i++) {
                String commCd = listStand.get(i).getStandResult();
                if (commCd.equals("13-01")) {
                    commCd = "A";
                }
                if (commCd.equals("13-02")) {
                    commCd = "B";
                }
                if (commCd.equals("13-03")) {
                    commCd = "C";
                }
                row.getCell(i + 2).setText(String.valueOf(commCd));
                row.getCell(i + 2).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
            }
        }

        // Adding Table to document
        XWPFParagraph p2 = doc.createParagraph();
        XWPFRun note = p2.createRun();
        note.setFontSize(12);
        note.setBold(true);
        note.setText("Chú thích: ");
        note.addBreak();
        List<TccoStd> listTcco = tccoSTDService.getCommCdByUpCommCd("13");
        for (int i = 0; i < listTcco.size(); i++) {
            XWPFRun rank = p2.createRun();
            rank.setFontSize(12);
            rank.setText("Bậc " + (i + 1) + ": " + listTcco.get(i).getDescription() + " (" + listTcco.get(i).getCommNm()
                    + ")");
            if (i < listTcco.size() - 1) {
                rank.addBreak();
            }
        }
    }

//    public static void mergeCellsHorizonal(XWPFTable table, int row, int fromCol, int toCol) {
//        if (toCol <= fromCol) return;
//        XWPFTableCell cell = table.getRow(row).getCell(fromCol);
//        CTTcPr tcPr = getTcPr(cell);
//        XWPFTableRow rowTable = table.getRow(row);
//        for (int colIndex = fromCol + 1; colIndex <= toCol; colIndex++) {
//          rowTable.getCtRow().removeTc(fromCol + 1);
//          rowTable.removeCell(fromCol + 1);
//        }
//        tcPr.addNewGridSpan();
//        tcPr.getGridSpan().setVal(BigInteger.valueOf((long) (toCol - fromCol + 1)));
//      }

    public void addTeacherInfo(XWPFDocument doc, SubjectModel model) {
        TsstUserInfo tsstUserInfo = model.getTsstUser().getTsstUserInfo();
        TsstUserInfoModel userInfoModel = new TsstUserInfoModel(tsstUserInfo);
        TeacherModel teacherModel = model.getTeacherModel();

        XWPFParagraph p1 = doc.createParagraph();
        p1.setAlignment(ParagraphAlignment.LEFT);

        XWPFRun teaTitle = p1.createRun();
        teaTitle.setFontSize(12);
        teaTitle.setBold(true);
        teaTitle.setText("1. Thông tin về giảng viên");
        teaTitle.addBreak();
        // info teacher
        XWPFRun tea = p1.createRun();
        tea.setFontSize(12);
        tea.setText("1.1. Giảng viên:");
        tea.addBreak();
        tea.setText("- Họ và tên: " + teacherModel.getFullName());
        tea.addBreak();
        tea.setFontSize(12);
        tea.setText("- Chức danh: Giảng viên");
        tea.addBreak();
        tea.setFontSize(12);
        tea.setText("- Địa chỉ liên hệ: " + teacherModel.getAddress());
        tea.addBreak();
        tea.setFontSize(12);
        tea.setText("- Điện thoại: " + teacherModel.getCellPhone());
        tea.addBreak();
        tea.setFontSize(12);
        tea.setText("- Họ và tên: " + teacherModel.getEmail());
        tea.addBreak();

        // info assist
        XWPFRun assist = p1.createRun();
        assist.setFontSize(12);
        assist.setText("1.2. Trợ giảng:");
        assist.addBreak();
        assist.setText("- Họ và tên: " + userInfoModel.getFullName());
        assist.addBreak();
        assist.setText("- Chức danh: Trợ Giảng");
        assist.addBreak();
        assist.setText("- Địa chỉ liên hệ: " + userInfoModel.getAddress());
        assist.addBreak();
        assist.setText("- Điện thoại: " + userInfoModel.getPhone());
        assist.addBreak();
        assist.setText("- Họ và tên: " + userInfoModel.getEmail());
    }

    public void addGoalSubject(XWPFDocument doc, SubjectModel model) {

        XWPFParagraph p1 = doc.createParagraph();
        p1.setAlignment(ParagraphAlignment.LEFT);

        XWPFRun subGoal = p1.createRun();
        subGoal.setFontSize(12);
        subGoal.setBold(true);
        subGoal.setText("2. Mục tiêu môn học:");
        subGoal.addBreak();

        XWPFRun goalContent = p1.createRun();
        goalContent.setFontSize(12);
        goalContent.setText("2.1 Mục tiêu chung");
        goalContent.addBreak();
        goalContent.setText(model.getSubjectGoal());

    }

    public void addListChapter(XWPFDocument doc, SubjectModel model) {
        XWPFParagraph p1 = doc.createParagraph();
        p1.setAlignment(ParagraphAlignment.LEFT);

        XWPFRun chapterTitle = p1.createRun();
        chapterTitle.setFontSize(12);
        chapterTitle.setBold(true);
        chapterTitle.setText("3. Nội dung môn học");
        chapterTitle.addBreak();

        XWPFRun chapterContent = p1.createRun();
        chapterContent.setFontSize(12);
        List<SbjChapterModel> chapterModel = model.getChapterModels();
        for (int i = 0; i < chapterModel.size(); i++) {
            chapterContent.setText("Chương " + (i + 1) + ": " + chapterModel.get(i).getChapterNm());
            chapterContent.addBreak();
        }
        XWPFRun noteChapter = p1.createRun();
        noteChapter.setFontSize(12);
        noteChapter.setText("Chi tiết xem thêm phần kế hoạch giảng dạy");
    }

    public void addTableDetailedSchedule(XWPFDocument doc, SubjectModel model) {
        List<SbjChapterModel> listChapter = model.getChapterModels();

        float[] pointColumnWidths = { 5, 15, 80 };
        XWPFTable table = doc.createTable();
        XWPFTableRow header = table.getRow(0);
        header.getCell(0).setText("STT.");
        header.getCell(0).setVerticalAlignment(XWPFVertAlign.CENTER);
        header.addNewTableCell().setText("Nội dung");
        header.getCell(1).setVerticalAlignment(XWPFVertAlign.CENTER);
        header.addNewTableCell().setText("Nội dung chi tiết");
        header.getCell(2).setVerticalAlignment(XWPFVertAlign.CENTER);
        table.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(500));
        table.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(1500));
        table.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(8000));
        int index = 1;
        for (int i = 0; i < listChapter.size(); i++) {

            List<LectureModel> listLecture = listChapter.get(i).getLectureModels();
            XWPFTableRow chapter = table.createRow();
            chapter.getCell(0).setText(String.valueOf(index));
            chapter.getCell(0).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
            chapter.getCell(1).setText(
                    String.valueOf(listChapter.get(i).getChapterNm() == null ? "" : listChapter.get(i).getChapterNm()));
            chapter.getCell(2);

            XWPFDocument docx = new XWPFDocument();
            XWPFParagraph p1 = docx.createParagraph();
            p1.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun chapterTitle = p1.createRun();
            chapterTitle.setFontSize(11);
            chapterTitle.setText("Chương " + String.valueOf(index) + ". " + listChapter.get(i).getChapterNm());
            chapterTitle.addBreak();
            for (int j = 0; j < listLecture.size(); j++) {
                chapterTitle.setText(String.valueOf(index) + "." + (j + 1) + ". " + listLecture.get(j).getLectureNm());
                chapterTitle.addBreak();

            }
            chapter.getCell(2).setParagraph(p1);
            index++;
        }

    }

    public void addPolicySubject(XWPFDocument doc, SubjectModel model) {
        XWPFParagraph p1 = doc.createParagraph();
        p1.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun policyTitle = p1.createRun();
        policyTitle.setFontSize(12);
        policyTitle.setBold(true);
        policyTitle.setText("5. Chính sách đối với môn học");
        policyTitle.addBreak();

        XWPFRun policy = p1.createRun();
        policy.setFontSize(12);
        policy.setText("- Sinh viên vắng mắt quá 20% số buổi sẽ không được làm bài thi kết thúc học phần.");
        policy.addBreak();
        policy.setText(
                "- Có điểm thưởng cho sinh viên tích cực phát biểu, ham học hỏi, có sự sáng tạo trong thảo luận, tranh luân.");

    }

    public void addTestMethod(XWPFDocument doc, SubjectModel model) {
        XWPFParagraph p1 = doc.createParagraph();
        p1.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun methodTitle = p1.createRun();
        methodTitle.setFontSize(12);
        methodTitle.setBold(true);
        methodTitle.setText("6. Phương pháp, hình thức kiểm tra - đángh giá kết quả học tập môn học");
        methodTitle.addBreak();
        XWPFRun method1 = p1.createRun();
        method1.setFontSize(12);
        method1.setText("- Bài kiểm tra trong kỳ, bài thi hết học phần: Thực hành");
        method1.addBreak();
        method1.setText("- Mô tả chi tiết:");
        method1.addBreak();
        method1.setText(
                "(1) Sinh viên sẽ làm 2 bài kiểm tra viết 60 phút vào giữa kỳ và cuối kỳ trong phạm vi nội dung đã được học");
        method1.addBreak();
        method1.setText("(2) Sinh viên sẽ làm bài thi kết thúc học phần theo hình thức thực hành.");
        method1.addBreak();
        method1.setText(
                "(3) Các bài kiểm tra thực hành này thể hiện kết quả học tập trên lớp, tự học, thực hành, làm bài tập mà sinh viên đã thực hiện cũng như khả năng vận dụng những kiến thức đã được học.");

    }

    public class DocumentPDF {
        private String path;
        private Document doc;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Document getDoc() {
            return doc;
        }

        public void setDoc(Document doc) {
            this.doc = doc;
        }

    }

    public String exportWordSubject(List<HashMap<String, Object>> list, SubjectModel model) throws Exception {
        String randomUID = UUID.randomUUID().toString();

        String afterDest = randomUID.replaceAll("[\\-\\+\\.\\!\\@\\#\\$\\%\\&\\*\\(\\)\\`\\~\\;\\/\\|\\?\\^:,]", "");
        // Creating a PdfDocument object
        String dest = pathUploadDir + afterDest + ".docx";
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph p1 = doc.createParagraph();
        p1.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun title1 = p1.createRun();
        title1.setBold(true);
        title1.setFontSize(14);
        title1.setText("ĐỀ CƯƠNG MÔN HỌC");
        title1.addBreak();
        title1.setText(model.getSubjectNm());
        title1.addBreak();

        // 1
        addTeacherInfo(doc, model);

        // 2

        addGoalSubject(doc, model);

        XWPFParagraph p2 = doc.createParagraph();
        p2.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun graph1 = p2.createRun();
        graph1.setFontSize(12);
        graph1.setText("2.2: Mục tiêu chi tiết với chuẩn đầu ra");
        graph1.addBreak();
        graph1.setText(
                "Chuẩn đầu ta của học phần là kiến thức, kỹ năng, thái độ, hành vi cần đạt được của sinh viên sau khi kế thúc học phần.");
        graph1.addBreak();
        graph1.setText("Sau khi học xong học phần, sinh viên có thể:");
        addTableStandardBySubject(doc, model);

        // 3
        addListChapter(doc, model);

        // 4
        XWPFParagraph p3 = doc.createParagraph();
        p3.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun grap = p3.createRun();
        grap.setFontSize(12);
        grap.setBold(true);
        grap.setText("4. Hình thức tổ chức dạy học (kế hoạch dạy học)");
        grap.addBreak();
        XWPFRun grapContent1 = p3.createRun();
        grapContent1.setText("4.1 Lịch trình chung");
        grapContent1.addBreak();
        addTableScheduleBySubject(doc, list);

        XWPFParagraph p4 = doc.createParagraph();
        p4.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun grapContent2 = p4.createRun();
        grapContent2.setText("4.2 Lịch trình chi tiết");
        grapContent2.addBreak();
        addTableDetailedSchedule(doc, model);

        // 5
        addPolicySubject(doc, model);

        // 6
        addTestMethod(doc, model);
        // Closing the document
        try (FileOutputStream out = new FileOutputStream(dest)) {
            doc.write(out);
        }
        doc.close();
        return afterDest;
    }

    public String exportWordSubject(String path) throws Exception {
        return "";
    }
}
