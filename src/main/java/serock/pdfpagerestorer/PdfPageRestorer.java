/*
Copyright (c) 2016 John Serock

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package serock.pdfpagerestorer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSObject;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class PdfPageRestorer implements Runnable {

    private File inPdfFile;
    private File outPdfFile;

    public PdfPageRestorer(final File inPdf, final File outPdf) {
        setInPdfFile(inPdf);
        setOutPdfFile(outPdf);
    }

    private static void addPages(final PDDocument pdDoc, final COSDocument cosDoc) throws IOException {
        final List<COSObject> pageObjects = cosDoc.getObjectsByType(COSName.PAGE);
        for (COSObject pageObject : pageObjects) {
            final COSBase       baseObject     = pageObject.getObject();
            final COSDictionary pageDictionary = (COSDictionary)baseObject;
            final PDPage        page           = new PDPage(pageDictionary);
            pdDoc.addPage(page);
        }
    }

    private static void removePages(final PDDocument pdDoc) {
        final int numberOfPages = pdDoc.getNumberOfPages();
        for (int i = numberOfPages - 1; i >= 0; i--) {
            pdDoc.removePage(i);
        }
    }

    @Override
    public void run() {
        try (final PDDocument  pdDoc  = PDDocument.load(getInPdfFile());
             final COSDocument cosDoc = pdDoc.getDocument()) {
            removePages(pdDoc);
            addPages(pdDoc, cosDoc);
            pdDoc.save(getOutPdfFile());
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public File getInPdfFile() {
        return this.inPdfFile;
    }

    public void setInPdfFile(final File inPdf) {
        this.inPdfFile = inPdf;
    }

    public File getOutPdfFile() {
        return this.outPdfFile;
    }

    public void setOutPdfFile(final File outPdf) {
        this.outPdfFile = outPdf;
    }

    public static void main(final String[] args) {
        final File            inPdf  = new File(args[0]);
        final File            outPdf = new File(args[1]);
        final PdfPageRestorer app    = new PdfPageRestorer(inPdf, outPdf);
        app.run();
    }
}
