package util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pojo.News;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class NewsUtil {
    //总行数
    private static int totalRows = 0;
    //总条数
    private static int totalCells = 0;
    //错误信息接收器
    private static String errorMsg;

//    public static List<News> getNews(MultipartFile mFile) {
    public static List<News> getNews(String fileName) {
//        String fileName = mFile.getOriginalFilename();
        List<News> newsList = null;
//        获取所需要导入的excel文件的文件名
        try {
//            如果不是这种类型的文件 直接报错
            if (!fileName.matches("^.+\\.(?i)(xlsx)$")) {
                return null;
            }
//            List<News> newsList = createExcel(mFile.getInputStream());
            newsList = createExcel(new FileInputStream(fileName));

        } catch (Exception e) {
            e.printStackTrace();
        }


        return newsList;
    }

    public static List<News> createExcel(InputStream is) throws IOException {
        Workbook wb = null;
        wb = new XSSFWorkbook(is);
        List<News> newsList = readExcelValue(wb);
        return newsList;

    }

    public static List<News> readExcelValue(Workbook wb) {
        //默认会跳过第一行标题
        // 得到第一个shell
//        Sheet sheet = wb.getSheet(String.valueOf(0));
        Sheet sheet = wb.getSheetAt(0);
//        获取Excel的行数
        totalRows = sheet.getPhysicalNumberOfRows();
//        获取Excel的列数
//        if (totalRows > 1 && sheet.getRow(0) != null){
//            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
//        }
        totalCells = 4;
//        这里我不获取了 直接写死 因为表示新闻的文件只有4列
        List<News> newsList = new ArrayList<>();


//        循环Excel行数
//        下列单词循环内的操作是 将每一行的数据读取出来 并将其包装为一个对象
        for (int r = 1; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null)
                continue;
            News news = new News();

//            循环Excel的每一列 即每一行中的每一列
            for (int c = 0; c < totalCells ; c++) {
                Cell cell = row.getCell(c);
//                获取单个单元格中的内容
                if (cell != null) {
//                    如果单元格不为空(单元格中有内容)
                    if (c == 0 || c == 1 || c == 2) {


//                        这里的三种情况一般都是返回空或者字符串类型 下面的判断语句仅仅作为 校验
                        if (cell.getCellType() == CellType.NUMERIC) {
                            cell.setCellType(CellType.STRING);
                        }

                        if (c == 0) {
                            news.setPic_url(cell.getStringCellValue());
                        } else if (c == 1) {
                            news.setTitle(cell.getStringCellValue());
                        } else if (c == 2) {
                            news.setMain(cell.getStringCellValue());
                        }


                    } else if (c == 3) {
                        LocalDate date = null;
//                        在excel中 日期值默认返回为"CELL_TYPE_NUMERIC"类型
                        if (cell.getCellType() == CellType.STRING) {
                            DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                            date = LocalDate.parse(cell.getStringCellValue(), dt);
                        }
                        news.setUpload_time(date);
                    }
                }
            }
            newsList.add(news);

        }
        return newsList;

    }
}
