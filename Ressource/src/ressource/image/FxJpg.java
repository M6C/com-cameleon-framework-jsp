package ressource.image;

import java.net.*;
import com.sun.image.codec.jpeg.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

import ressource.FxFile;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */

public class FxJpg {

	public FxJpg() {
	}

	/**
	 * Write a liste of image from an url to a destination path
	 * 
	 * @param url
	 * @param lstImage
	 * @param szDestinationPath
	 * @return 
	 */
	public static void writeFromUrl(URL url, Vector lstImage, String szDestinationPath) {
		for (int i = 0; i < lstImage.size(); i++) {
			Vector item = (Vector) lstImage.elementAt(i);
			String szName = (String) item.elementAt(0);
			Vector data = (Vector) item.elementAt(1);
			for (int j = 0; j < data.size(); j++) {
				String link = (String) data.elementAt(j);
				try {
					URL imgUrl = new URL(url, link);
					writeFromUrl(imgUrl, szDestinationPath);
				} catch (MalformedURLException ex) {
					// TRACE
					ex.printStackTrace();
				} catch (IOException ex) {
					// TRACE
					ex.printStackTrace();
				} catch (Exception ex) {
					// TRACE
					ex.printStackTrace();
				}
			}
		}
	}

	/**
	 * Write an Jpg Image from an url to a destination path
	 * 
	 * @param url
	 * @param szDestPath
	 * @return 
	 */
	public static String writeFromUrl(URL url, String szDestPath) {
		try {
			return writeFromUrl(url.openConnection(), szDestPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Write an Jpg Image from an url to a destination path
	 * 
	 * @param url
	 * @param szDestPath
	 * @return 
	 */
	public static String writeFromUrlJPEGEncoder(URLConnection url, String szDestPath) {
		try {
			File theFile = FxFile.buildFileUnique(url.getURL(), szDestPath);
			JPEGImageDecoder enc = JPEGCodec.createJPEGDecoder(url.getInputStream());
			BufferedImage bufImg = enc.decodeAsBufferedImage();
			JPEGCodec.createJPEGEncoder(new FileOutputStream(theFile)).encode(bufImg);
			return theFile.getCanonicalPath();
		} catch (MalformedURLException ex) {
			// TRACE
			ex.printStackTrace();
		} catch (IOException ex) {
			// TRACE
			ex.printStackTrace();
		}
		return "";
	}
	
	/**
	 * Write Image from an url to a destination path
	 * 
	 * @param url
	 * @param szDestPath
	 * @return 
	 */
	public static String writeFromUrl(URLConnection url, String szDestPath) {
		return FxFile.writeFromUrl(url, szDestPath);
	}
}