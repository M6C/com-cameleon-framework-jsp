package ressource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FxFile {
	
	private FxFile() {
	}

	public static File buildFileUnique(File file) {
		int idx = 1;
		String fileName = file.getName();
		String path = file.getParent();
		while(file.exists()) {
			String name = fileName;
			int pnt = name.lastIndexOf(".");
			if (pnt>0)
				name = name.substring(0, pnt) + idx + name.substring(pnt);
			else
				name += idx;
			file = new File(path, name);
			idx++;
		}
		return file;
	}
	
	public static File buildFileUnique(URL url, String szDestPath) {
		String fileName = url.getFile();
		int iSlash = fileName.lastIndexOf('/');
		if (iSlash>0)
			fileName = fileName.substring(iSlash + 1);
		if (!szDestPath.endsWith("/"))
			szDestPath = szDestPath.concat("/");
		return FxFile.buildFileUnique(new File(szDestPath, fileName));
	}
	
	/**
	 * Write File from an url to a destination path
	 * 
	 * @param url
	 * @param szDestPath
	 * @return 
	 */
	public static String writeFromUrl(URLConnection url, String szDestPath) {
		InputStream is = null;
		OutputStream os = null;
		File theFile = FxFile.buildFileUnique(url.getURL(), szDestPath);
		try {
			is = url.getInputStream();
			os = new FileOutputStream(theFile);

			byte[] b = new byte[2048];
			int length;

			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}
			return theFile.getCanonicalPath();
		} catch (MalformedURLException ex) {
			// TRACE
			ex.printStackTrace();
		} catch (IOException ex) {
			// TRACE
			ex.printStackTrace();
		}
		finally {
			if (is!=null) {
				try {
					is.close();
				} catch (IOException e) {
					// TRACE
					e.printStackTrace();
				}
			}
			if (os!=null) {
				try {
					os.close();
				} catch (IOException e) {
					// TRACE
					e.printStackTrace();
				}
			}
		}
		return "";
	}
}
