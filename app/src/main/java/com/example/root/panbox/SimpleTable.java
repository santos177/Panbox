package com.example.root.panbox;

/**
 * Created by root on 24-09-17.
 */

import java.io.IOException;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
public class SimpleTable {


    public Boolean write(String fname, String fcontent,String [] data) {
        try {
            String fpath = "/sdcard/" + fname + ".pdf";
            File file = new File(fpath);

            if (!file.exists()) {
                file.createNewFile();
            }

            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);


            Document document = new Document();

            PdfWriter.getInstance(document,
                    new FileOutputStream(file.getAbsoluteFile()));
            document.open();
            Paragraph titulo = new Paragraph("TITULO DEL ARCHIVO");
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add( Chunk.NEWLINE );
            PdfPTable table = new PdfPTable(6);
            String [] header ={"Cliente","Precio unitario","Total Pan","Saldo Anterior","Total","Saldo"};
            for(int i = 0; i < header.length ; i++){
                table.addCell(header[i]);
            }
            for(int i = 0; i < data.length; i++){
                table.addCell(data[i]);
            }
            document.add(table);
            document.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }}
