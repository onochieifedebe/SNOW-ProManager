package com.enatcpromanager.com.utilities;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.enatcpromanager.com.entities.Project;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;


public class projPdfExporter {

	private List<Project> projects;

	public projPdfExporter(List<Project> projects) {
		this.projects = projects;
	}

	private void writeTableHeader(PdfPTable table) {
		String[] headerCells = {"Project Name","Start Date","End Date", "Rscs Needed", "Rscs Assigned", "Client Needs", "Status"};

		PdfPCell cell = new PdfPCell();
		cell.setPadding(5);

		Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA);
		fontTitle.setSize(12);

		for ( String headerCell : headerCells) {
			cell.setPhrase(new Phrase(headerCell, fontTitle));
			table.addCell(cell);
		}


	}

	private void writeTableData(PdfPTable table) {

		for (Project project : projects) {
			table.addCell(String.valueOf(project.getName()));
			table.addCell(String.valueOf(project.getStartDate()));
			table.addCell(String.valueOf(project.getEndDate()));
			table.addCell(String.valueOf(project.getResourcesNeeded()));
			table.addCell(String.valueOf(project.getResourcesAssigned()));
			table.addCell(String.valueOf(project.getDescription()));
			table.addCell(String.valueOf(project.getStatus()));
		}
	}

	public void export(HttpServletResponse response) throws DocumentException, IOException {

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();
		Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontTitle.setSize(15);

		Paragraph headParagraph = new Paragraph("LIST OF PROJECTS", fontTitle);
		headParagraph.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(headParagraph);

		PdfPTable table = new PdfPTable(7);
		table.setWidthPercentage(100);
		table.setSpacingBefore(15);
		table.setWidths(new float[] {2.5f, 2.0f, 2.0f, 1.5f, 1.7f, 2.7f, 2.5f});

		writeTableHeader(table);
		writeTableData(table);


		document.add(table);

		document.close();

	}

}
