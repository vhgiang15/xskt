package com.ungdungso.business;
import java.awt.Color;
import java.io.IOException;
import java.util.List;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.ungdungso.model.LotteryResults;
import com.ungdungso.model.PrizeValue;
import com.ungdungso.model.Province;


import jakarta.servlet.http.HttpServletResponse;

public class UserPDFExporterByDate {
	private List<Province> provincesList;
	private List<LotteryResults> lotteryResultsList;
	private List<PrizeValue> prizeValueList;
	private String dateString;

	public UserPDFExporterByDate() {
	}
	



	public UserPDFExporterByDate(List<Province> provincesList, List<LotteryResults> lotteryResultsList,
			List<PrizeValue> prizeValueList, String dateString) {
		super();
		this.provincesList = provincesList;
		this.lotteryResultsList = lotteryResultsList;
		this.prizeValueList = prizeValueList;
		this.dateString = dateString;
	}




	private void writeTableHeaderMN(Table table) throws DocumentException, IOException {
        BaseFont bfTahoma = BaseFont.createFont("C:\\Windows\\Fonts\\Tahoma.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bfTahoma, 10);
        Cell cell = new Cell(new Phrase("KẾT QUẢ XỔ SỐ MIỀN NAM NGÀY "+ dateString, font));
        cell.setHeader(true);
        if(provincesList.size()==3) {
        cell.setColspan(4);} else {cell.setColspan(5);};
        table.addCell(cell);
        table.endHeaders();
        cell= new Cell(new Phrase("Tên Giải", font));
        cell.setBackgroundColor(Color.WHITE);
        font.setColor(Color.BLACK);  
        cell.setBackgroundColor(Color.WHITE);
        table.addCell(cell);
        for (Province province : provincesList) {
        	cell= new Cell(new Phrase(province.getNameProvince(), font));
            table.addCell(cell);
		}
    }
	
	
	
	
	private void writeTableHeaderMT(Table table) throws DocumentException, IOException {
        BaseFont bfTahoma = BaseFont.createFont("C:\\Windows\\Fonts\\Tahoma.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bfTahoma, 10);
        Cell cell = new Cell(new Phrase("KẾT QUẢ XỔ SỐ MIỀN TRUNG NGÀY "+ dateString, font));
        cell.setHeader(true);
        if(provincesList.size()==2) {
        cell.setColspan(3);} else {cell.setColspan(4);};
        table.addCell(cell);
        table.endHeaders();
        cell= new Cell(new Phrase("Tên Giải", font));
        cell.setBackgroundColor(Color.WHITE);
        font.setColor(Color.BLACK);  
        cell.setBackgroundColor(Color.WHITE);
        table.addCell(cell);
        for (Province province : provincesList) {
        	cell= new Cell(new Phrase(province.getNameProvince(), font));
            table.addCell(cell);
		}
    }
	
	
	private void writeTableHeaderMB(Table table) throws DocumentException, IOException {
        BaseFont bfTahoma = BaseFont.createFont("C:\\Windows\\Fonts\\Tahoma.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bfTahoma, 10);
        Cell cell = new Cell(new Phrase("KẾT QUẢ XỔ SỐ MIỀN BẮC NGÀY "+ dateString, font));
        cell.setHeader(true);
        cell.setColspan(2);
        table.addCell(cell);
        table.endHeaders();
        cell= new Cell(new Phrase("Tên Giải", font));
        cell.setBackgroundColor(Color.WHITE);
        font.setColor(Color.BLACK);  
        cell.setBackgroundColor(Color.WHITE);
        table.addCell(cell);
        for (Province province : provincesList) {
        	cell= new Cell(new Phrase(province.getNameProvince(), font));
            table.addCell(cell);
		}
    }
		
	private void writeTableDataMN(Table table) throws DocumentException, IOException {
		if(provincesList.size()==3) {
			String[] arrResult0 = lotteryResultsList.get(0).getResult().split("-");
			String[] arrResult1 = lotteryResultsList.get(1).getResult().split("-");
			String[] arrResult2 = lotteryResultsList.get(2).getResult().split("-");
			for (int i=0; i<prizeValueList.size()-2; i++) {
				table.addCell(prizeValueList.get(i).getNamePrize());
				if(i<3) {				
					table.addCell(arrResult0[i]);
					table.addCell(arrResult1[i]);
					table.addCell(arrResult2[i]);}
				if(i==3) {
					table.addCell(arrResult0[3]+" "+arrResult0[4]);
					table.addCell(arrResult1[3]+" "+arrResult1[4]);
					table.addCell(arrResult2[3]+" "+arrResult2[4]);
					
				}
				if(i==4) {
					table.addCell(arrResult0[5]+" "+arrResult0[6]+" "+arrResult0[7]+" "+arrResult0[8]+" "+arrResult0[9]+" "+arrResult0[10]+" "+arrResult0[11]);
					table.addCell(arrResult1[5]+" "+arrResult1[6]+" "+arrResult1[7]+" "+arrResult1[8]+" "+arrResult1[9]+" "+arrResult1[10]+" "+arrResult1[11]);
					table.addCell(arrResult2[5]+" "+arrResult2[6]+" "+arrResult2[7]+" "+arrResult2[8]+" "+arrResult2[9]+" "+arrResult2[10]+" "+arrResult2[11]);
				}
				if(i==5) {
					table.addCell(arrResult0[12]);
					table.addCell(arrResult1[12]);
					table.addCell(arrResult2[12]);
				}
				if(i==6) {
					table.addCell(arrResult0[13]+" "+arrResult0[14]+" "+arrResult0[15]);
					table.addCell(arrResult1[13]+" "+arrResult1[14]+" "+arrResult1[15]);
					table.addCell(arrResult2[13]+" "+arrResult2[14]+" "+arrResult2[15]);
				}
				if(i==7) {
					table.addCell(arrResult0[16]);
					table.addCell(arrResult1[16]);
					table.addCell(arrResult2[16]);
				}
				if(i==8) {
					table.addCell(arrResult0[17]);
					table.addCell(arrResult1[17]);
					table.addCell(arrResult2[17]);
				}
			}
		} else {
			String[] arrResult0 = lotteryResultsList.get(0).getResult().split("-");
			String[] arrResult1 = lotteryResultsList.get(1).getResult().split("-");
			String[] arrResult2 = lotteryResultsList.get(2).getResult().split("-");
			String[] arrResult3 = lotteryResultsList.get(3).getResult().split("-");	
			for (int i=0; i<prizeValueList.size()-2; i++) {
				table.addCell(prizeValueList.get(i).getNamePrize());
				if(i<3) {				
					table.addCell(arrResult0[i]);
					table.addCell(arrResult1[i]);
					table.addCell(arrResult2[i]);
					table.addCell(arrResult3[i]);	
				}
				
				if(i==3) {
					table.addCell(arrResult0[3]+" "+arrResult0[4]);
					table.addCell(arrResult1[3]+" "+arrResult1[4]);
					table.addCell(arrResult2[3]+" "+arrResult2[4]);
					table.addCell(arrResult3[3]+" "+arrResult3[4]);
				}
				if(i==4) {
					table.addCell(arrResult0[5]+" "+arrResult0[6]+" "+arrResult0[7]+" "+arrResult0[8]+" "+arrResult0[9]+" "+arrResult0[10]+" "+arrResult0[11]);
					table.addCell(arrResult1[5]+" "+arrResult1[6]+" "+arrResult1[7]+" "+arrResult1[8]+" "+arrResult1[9]+" "+arrResult1[10]+" "+arrResult1[11]);
					table.addCell(arrResult2[5]+" "+arrResult2[6]+" "+arrResult2[7]+" "+arrResult2[8]+" "+arrResult2[9]+" "+arrResult2[10]+" "+arrResult2[11]);
					table.addCell(arrResult3[5]+" "+arrResult3[6]+" "+arrResult3[7]+" "+arrResult3[8]+" "+arrResult3[9]+" "+arrResult3[10]+" "+arrResult3[11]);
				}
				if(i==5) {
					table.addCell(arrResult0[12]);
					table.addCell(arrResult1[12]);
					table.addCell(arrResult2[12]);
					table.addCell(arrResult3[12]);
				}
				if(i==6) {
					table.addCell(arrResult0[13]+" "+arrResult0[14]+" "+arrResult0[15]);
					table.addCell(arrResult1[13]+" "+arrResult1[14]+" "+arrResult1[15]);
					table.addCell(arrResult2[13]+" "+arrResult2[14]+" "+arrResult2[15]);
					table.addCell(arrResult3[13]+" "+arrResult3[14]+" "+arrResult3[15]);
				}
				if(i==7) {
					table.addCell(arrResult0[16]);
					table.addCell(arrResult1[16]);
					table.addCell(arrResult2[16]);
					table.addCell(arrResult3[16]);
				}
				if(i==8) {
					table.addCell(arrResult0[17]);
					table.addCell(arrResult1[17]);
					table.addCell(arrResult2[17]);
					table.addCell(arrResult3[17]);
				}
			}	
		}	
    }
	
	
	private void writeTableDataMT(Table table) throws DocumentException, IOException {
		if(provincesList.size()==2) {
			String[] arrResult0 = lotteryResultsList.get(0).getResult().split("-");
			String[] arrResult1 = lotteryResultsList.get(1).getResult().split("-");
			for (int i=0; i<prizeValueList.size()-2; i++) {
				table.addCell(prizeValueList.get(i).getNamePrize());
				if(i<3) {				
					table.addCell(arrResult0[i]);
					table.addCell(arrResult1[i]);
				}
				if(i==3) {
					table.addCell(arrResult0[3]+" "+arrResult0[4]);
					table.addCell(arrResult1[3]+" "+arrResult1[4]);
					
				}
				if(i==4) {
					table.addCell(arrResult0[5]+" "+arrResult0[6]+" "+arrResult0[7]+" "+arrResult0[8]+" "+arrResult0[9]+" "+arrResult0[10]+" "+arrResult0[11]);
					table.addCell(arrResult1[5]+" "+arrResult1[6]+" "+arrResult1[7]+" "+arrResult1[8]+" "+arrResult1[9]+" "+arrResult1[10]+" "+arrResult1[11]);
				}
				if(i==5) {
					table.addCell(arrResult0[12]);
					table.addCell(arrResult1[12]);
				}
				if(i==6) {
					table.addCell(arrResult0[13]+" "+arrResult0[14]+" "+arrResult0[15]);
					table.addCell(arrResult1[13]+" "+arrResult1[14]+" "+arrResult1[15]);
				}
				if(i==7) {
					table.addCell(arrResult0[16]);
					table.addCell(arrResult1[16]);
				}
				if(i==8) {
					table.addCell(arrResult0[17]);
					table.addCell(arrResult1[17]);

				}
			}
		} else {
			String[] arrResult0 = lotteryResultsList.get(0).getResult().split("-");
			String[] arrResult1 = lotteryResultsList.get(1).getResult().split("-");
			String[] arrResult2 = lotteryResultsList.get(2).getResult().split("-");

			for (int i=0; i<prizeValueList.size()-2; i++) {
				table.addCell(prizeValueList.get(i).getNamePrize());
				if(i<3) {				
					table.addCell(arrResult0[i]);
					table.addCell(arrResult1[i]);
					table.addCell(arrResult2[i]);

				}
				
				if(i==3) {
					table.addCell(arrResult0[3]+" "+arrResult0[4]);
					table.addCell(arrResult1[3]+" "+arrResult1[4]);
					table.addCell(arrResult2[3]+" "+arrResult2[4]);

				}
				if(i==4) {
					table.addCell(arrResult0[5]+" "+arrResult0[6]+" "+arrResult0[7]+" "+arrResult0[8]+" "+arrResult0[9]+" "+arrResult0[10]+" "+arrResult0[11]);
					table.addCell(arrResult1[5]+" "+arrResult1[6]+" "+arrResult1[7]+" "+arrResult1[8]+" "+arrResult1[9]+" "+arrResult1[10]+" "+arrResult1[11]);
					table.addCell(arrResult2[5]+" "+arrResult2[6]+" "+arrResult2[7]+" "+arrResult2[8]+" "+arrResult2[9]+" "+arrResult2[10]+" "+arrResult2[11]);

				}
				if(i==5) {
					table.addCell(arrResult0[12]);
					table.addCell(arrResult1[12]);
					table.addCell(arrResult2[12]);

				}
				if(i==6) {
					table.addCell(arrResult0[13]+" "+arrResult0[14]+" "+arrResult0[15]);
					table.addCell(arrResult1[13]+" "+arrResult1[14]+" "+arrResult1[15]);
					table.addCell(arrResult2[13]+" "+arrResult2[14]+" "+arrResult2[15]);

				}
				if(i==7) {
					table.addCell(arrResult0[16]);
					table.addCell(arrResult1[16]);
					table.addCell(arrResult2[16]);

				}
				if(i==8) {
					table.addCell(arrResult0[17]);
					table.addCell(arrResult1[17]);
					table.addCell(arrResult2[17]);

				}
			}	
		}	
    }
	
	private void writeTableDataMB(Table table) throws DocumentException, IOException {
			String[] arrResult0 = lotteryResultsList.get(0).getResult().split("-");
			for (int i=0; i<7; i++) {
				table.addCell(prizeValueList.get(i).getNamePrize());
				if(i<2) {				
					table.addCell(arrResult0[i]);

				}
				if(i==2) {
					table.addCell(arrResult0[2]+" "+arrResult0[3]);					
				}
				if(i==3) {
					table.addCell(arrResult0[4]+" "+arrResult0[5]+" "+arrResult0[6]+" "+arrResult0[7]+" "+arrResult0[8]+" "+arrResult0[9]);

				}
				if(i==4) {
					table.addCell(arrResult0[10]+" "+arrResult0[11]+" "+arrResult0[12]+" "+arrResult0[13]);

				}
				if(i==5) {
					table.addCell(arrResult0[14]+" "+arrResult0[15]+" "+arrResult0[16]+" "+arrResult0[17]+" "+arrResult0[18]+" "+arrResult0[19]);

				}
				if(i==6) {
					table.addCell(arrResult0[20]+" "+arrResult0[21]+" "+arrResult0[22]);

				}
				

			}	
		}	
   
	
	
	
	
  
    public void exportMN(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A5.rotate());
        document.setMargins(5,5,5,5);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        BaseFont bfTahoma = BaseFont.createFont("C:\\Windows\\Fonts\\Tahoma.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bfTahoma, 12);
        font.setColor(Color.BLACK); 
        Table table0= new Table(3, 1);
        table0.setWidth(100);
        table0.setPadding(2);
        table0.setWidths(new float[] {1.0f, 0.1f, 1.0f});
        int amountProvince= provincesList.size();
        if(amountProvince==3) {
	        Table table1 = new Table(4);
	        table1.setWidth(40);
	        table1.setPadding(2);
	        table1.setWidths(new float[] {1.0f, 0.8f, 0.8f, 0.8f});
	        writeTableHeaderMN(table1);
	        writeTableDataMN(table1);
	        table0.insertTable(table1, 0, 0);
	        table0.insertTable(table1, 0, 2);
        }
        
        if(amountProvince==4) {
            Table table1 = new Table(5);
            table1.setWidth(40);
            table1.setPadding(2);
            table1.setWidths(new float[] {1.0f, 0.8f, 0.8f, 0.8f,0.8f});
            writeTableHeaderMN(table1);
            writeTableDataMN(table1);
            table0.insertTable(table1, 0, 0);
            table0.insertTable(table1, 0, 2);
            }
  
        document.add(table0);
        document.close();
         
    }
    
    public void exportMT(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A5.rotate());
        document.setMargins(5,5,5,5);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        BaseFont bfTahoma = BaseFont.createFont("C:\\Windows\\Fonts\\Tahoma.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bfTahoma, 12);
        font.setColor(Color.BLACK); 
        Table table0= new Table(3, 1);
        table0.setWidth(100);
        table0.setPadding(2);
        table0.setWidths(new float[] {1.0f, 0.1f, 1.0f});
        int amountProvince= provincesList.size();
        if(amountProvince==2) {
	        Table table1 = new Table(3);
	        table1.setWidth(40);
	        table1.setPadding(2);
	        table1.setWidths(new float[] {1.0f, 0.8f, 0.8f});
	        writeTableHeaderMT(table1);
	        writeTableDataMT(table1);
	        table0.insertTable(table1, 0, 0);
	        table0.insertTable(table1, 0, 2);
        }
        
        if(amountProvince==3) {
            Table table1 = new Table(5);
            table1.setWidth(40);
            table1.setPadding(2);
            table1.setWidths(new float[] {1.0f, 0.8f, 0.8f, 0.8f});
            writeTableHeaderMT(table1);
            writeTableDataMT(table1);
            table0.insertTable(table1, 0, 0);
            table0.insertTable(table1, 0, 2);
            }
  
        document.add(table0);
        document.close();
         
    }
    
    public void exportMB(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A5.rotate());
        document.setMargins(5,5,5,5);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        BaseFont bfTahoma = BaseFont.createFont("C:\\Windows\\Fonts\\Tahoma.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bfTahoma, 12);
        font.setColor(Color.BLACK); 
        Table table0= new Table(3, 1);
        table0.setWidth(100);
        table0.setPadding(2);
        table0.setWidths(new float[] {1.0f, 0.1f, 1.0f});
	    Table table1 = new Table(2);
	    table1.setWidth(40);
	    table1.setPadding(2);
	    table1.setWidths(new float[] {1.0f, 1.8f});
	    writeTableHeaderMB(table1);
	    writeTableDataMB(table1);
	    table0.insertTable(table1, 0, 0);
	    table0.insertTable(table1, 0, 2);
        document.add(table0);
        document.close();
         
    }
    
	
	

}
