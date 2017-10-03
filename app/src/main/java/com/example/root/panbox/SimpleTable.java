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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleTable {


    public Boolean write(String fname, String fcontent,String [] data,String [] balance,String[] bread) {
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
            Paragraph titulo = new Paragraph("PANADERIA LA PALOMA");
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            Paragraph titulo2 = new Paragraph("Reparto Osvaldo");
            titulo2.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo2);
            DateFormat dateFormat = new SimpleDateFormat("MMM d,EEE,''yyyy");
            Date date = new Date();
            String fecha = date.toString();
            Paragraph titulo3 = new Paragraph(fecha);
            titulo3.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo3);
            PdfPTable table = new PdfPTable(6);
            String [] header ={"Cliente","Precio unitario","Total Pan","Saldo Anterior","Total","Saldo"};
            for(int i = 0; i < header.length ; i++){
                table.addCell(header[i]);
            }
            for(int i = 0; i < data.length; i++){
                table.addCell(data[i]);
            }
            table.addCell("TOTALES");  // fila de totales!
            table.addCell(bread[0]+"uni"); // total de pan
            table.addCell(bread[1]+"kgs");
            for(int i = 0; i < balance.length; i++){  // saldo anterior total, dinero total, saldo total
                table.addCell(balance[i]);
            }
            table.addCell("Repartos");
            table.addCell("uni.");
            table.addCell("kgs");
            table.addCell("Tot. Bruto:$");
            table.addCell("");
            table.addCell("");
            table.addCell("1er");
            table.addCell("");
            table.addCell("");
            table.addCell("Sueldo: $");
            table.addCell("");
            table.addCell("");
            table.addCell("2do");
            table.addCell("");
            table.addCell("");
            table.addCell("Gastos: $");
            table.addCell("");
            table.addCell("");
            table.addCell("3er");
            table.addCell("");
            table.addCell("");;
            table.addCell("Total: $");
            table.addCell("");
            table.addCell("");
            table.addCell("4to");
            for(int i = 0; i < 5; i++){  // saldo anterior total, dinero total, saldo total
                table.addCell("");
            }
            table.addCell("devolución");
            for(int i = 0; i < 5; i++){  // saldo anterior total, dinero total, saldo total
                table.addCell("");
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
