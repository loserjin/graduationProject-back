//package com.canteen.util;
//
//import com.canteen.common.dto.PurchaseDto;
//import com.itextpdf.text.pdf.PdfWriter;
//
//import javax.swing.text.Document;
//import java.io.IOException;
//import java.util.List;
//
//public class PdfUtil {
//
//    public void createPDF(Document document, PdfWriter writer, List<PurchaseDto> purchaseDtoList) throws IOException {
//        //Document document = new Document(PageSize.A4);
//        try {
//            document.addTitle("sheet of product");
//            document.addAuthor("scurry");
//            document.addSubject("product sheet.");
//            document.addKeywords("product.");
//            document.open();
//            PdfPTable table = createTable(writer,products);
//            document.add(table);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        } finally {
//            document.close();
//        }
//    }
//    public static PdfPTable createTable(PdfWriter writer,List<Product> products) throws IOException, DocumentException {
//        PdfPTable table = new PdfPTable(3);//生成一个两列的表格
//        PdfPCell cell;
//        int size = 20;
//        Font font = new Font(BaseFont.createFont("C://Windows//Fonts//simfang.ttf", BaseFont.IDENTITY_H,
//                BaseFont.NOT_EMBEDDED));
//        for(int i = 0;i<products.size();i++) {
//            cell = new PdfPCell(new Phrase(products.get(i).getProductCode(),font));//产品编号
//            cell.setFixedHeight(size);
//            table.addCell(cell);
//            cell = new PdfPCell(new Phrase(products.get(i).getProductName(),font));//产品名称
//            cell.setFixedHeight(size);
//            table.addCell(cell);
//            cell = new PdfPCell(new Phrase(products.get(i).getPrice()+"",font));//产品价格
//            cell.setFixedHeight(size);
//            table.addCell(cell);
//        }
//        return table;
//    }
//}
