package cz.tasks.primenumbers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Hlavní třída aplikace. 
 * 
 * Aplikace zpracuje vstupní soubor formátu XLSX a vypíše prvočísla, které se 
 * nachází v prvním listu ve sloupci B. Vypisuje pouze prvočísla uvedená 
 * v textovém formátu od 1 do 9,223,372,036,854,775,807.
 */
public class PrimeNumbers {

    /**
     * Metoda zjistí, zda je dané číslo prvočíslem.
     * @param value Číslo, u kterého se zjišťuje, zda jde o prvočíslo.
     * @return TRUE, jedná-li se o prvočíslo, jinak FALSE.
     */
    private static boolean isPrime(long value){
        
        if(value <= 1)
            return false;
        
        for (int i = 2; i < value; i++){
            if (value % i == 0){
                return false;
            }
        }
         
        return true;
    }
    
    /**
     * Hlavní metoda aplikace.
     * @param args Parametry předávané při spuštění aplikace.
     */
    public static void main(String[] args) {
        
        FileInputStream stream = null;
        
        try {
            if(args.length == 0){
                System.err.println("No input file was specified.");
                return;
            } 
            
            stream = new FileInputStream(new File(args[0]));
            Workbook workbook = new XSSFWorkbook(stream);
            
            Sheet sheet = workbook.getSheetAt(0);
            
            Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()) {
            
                Row row = rowIterator.next();
                Cell cell = row.getCell(1);
                        
                if(cell.getCellType() == STRING){
                    String str = cell.getStringCellValue();
                    
                    try {
                        long value = Long.parseLong(str);
                        if(isPrime(value)){
                            System.out.println(value);
                        }
                    } catch (NumberFormatException exp) {
                        
                    }
                }
            }
            
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            if(stream != null){
                try {
                    stream.close();
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }
}
