package com.abs.ps.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

public final class BarcodeUtil {
	private BarcodeUtil() {}
	
	public static String generateBarcode(HttpServletRequest request, String code) {
		if (StringHelper.isEmpty(code)) {
			return "";
		}
		String contextPath = request.getSession().getServletContext().getRealPath("/");
		String fileName = contextPath + "/temp/barcode.png";

		File file = new File(fileName);
		Code128Bean bean = new Code128Bean();
		final int dpi = 2000;
		 
        // barcode
        bean.setModuleWidth(0.55);
        bean.setHeight(20);
        bean.doQuietZone(false);
        bean.setQuietZone(0);
        bean.setFontSize(7);
        bean.setMsgPosition(HumanReadablePlacement.HRP_BOTTOM);
         
        OutputStream out = null;
 
        try {
            out = new FileOutputStream(file);
 
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(out,
                    "image/png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
            bean.generateBarcode(canvas, code);
            canvas.finish();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return request.getContextPath() + "/temp/barcode.png";
	}
	
}
