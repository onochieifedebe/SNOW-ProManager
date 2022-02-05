package com.enatcpromanager.com.utilities;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.enatcpromanager.com.dto.ProjectEmployee;
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

public class EmpOnProjExporter {

	List<ProjectEmployee> projectemployeeList;

	public EmpOnProjExporter(List<ProjectEmployee> projectemployeeList) {
		this.projectemployeeList = projectemployeeList;
	}

	private void writeTableHeader(PdfPTable table) {
		String[] headerCells = {"Project Name","First Name","Last Name", "CL Level", "Status"};

		PdfPCell cell = new PdfPCell();
		cell.setPadding(5);

		Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA);
		fontTitle.setSize(15);

		for ( String headerCell : headerCells) {
			cell.setPhrase(new Phrase(headerCell, fontTitle));
			table.addCell(cell);
		}

	}

	private void writeTableData(PdfPTable table) {

		for (ProjectEmployee proEmp : projectemployeeList) {
			table.addCell(String.valueOf(proEmp.getClientName()));
			table.addCell(String.valueOf(proEmp.getFirstName()));
			table.addCell(String.valueOf(proEmp.getLastName()));
			table.addCell(String.valueOf(proEmp.getClLevel()));
			table.addCell(String.valueOf(proEmp.getStatus()));
		}
	}

	public void export(HttpServletResponse response) throws DocumentException, IOException {

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();
		Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontTitle.setSize(15);

		Paragraph headParagraph = new Paragraph("LIST OF EMPLOYEES ON PROJECTS", fontTitle);
		headParagraph.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(headParagraph);

		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100);
		table.setSpacingBefore(15);
		table.setWidths(new float[] {3.0f, 2.0f, 2.0f, 2.0f, 3.0f});

		writeTableHeader(table);
		writeTableData(table);


		document.add(table);

		document.close();

	}


}
