package reader;

/**
 * Created by Ivan on 21.01.2015.
 */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import models.Lift;
import models.Skiarena;
import play.Play;
import play.db.ebean.Model;

public class ReadExcel {

    private String inputFile;

    private int nameColumn=0;
    private int skiColumn=3;
    private int seatsColumn=14;
    private int rows=3;
    public void read()  {
        try {
            InputStream inputWorkbook = Play.application().classloader().getResourceAsStream("Aufstiegshilfen.xls");

            Workbook w;
            try {
                w = Workbook.getWorkbook(inputWorkbook);
                // Get the first sheet
                Sheet sheet = w.getSheet(w.getNumberOfSheets()-1);

                // Loop over first 10 column and lines
                readRows(sheet);

            } catch (BiffException e) {
                e.printStackTrace();
            }
        }catch (IOException e){

        }
    }

    private void readRows(Sheet sheet) {
        Skiarena test= new Skiarena(1000,"");
        test.save();
        for (int i = rows; i < sheet.getRows(); i++) {
            String name=sheet.getCell(nameColumn,i).getContents();
            String skiarena=sheet.getCell(skiColumn,i).getContents();
            int seats;
            try{
                seats=Integer.parseInt(sheet.getCell(seatsColumn,i).getContents());
            } catch (Exception e){
                seats=0;
            }
            Skiarena s= new Skiarena(Skiarena.getMaxId(),skiarena);
            Skiarena sList=Skiarena.getByName(skiarena);
            if(sList!=null)
                s=sList;
            else
                s.save();
            Lift l= new Lift(name,s,seats);
            l.save();

        }
    }



}