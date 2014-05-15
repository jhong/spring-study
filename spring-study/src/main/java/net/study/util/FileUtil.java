package net.study.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {

	private final static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * directory 생성하기
	 * @param dirPath
	 */
	public static void makeDir(String dirPath) {
		if (StringUtil.isEmpty(dirPath)) {
			logger.warn("makeDir() dirPath is null!!!");
			return;
		}
		
		File file = new File(dirPath);
	    if (!file.isDirectory()) {
			boolean flag = file.mkdirs(); // 상위디렉토리까지 생성 (2013.10.11)
			if (!flag) {
				logger.error("fail in directory creation... file="+file);
//			    throw new IOException("fail in directory creation...");
			}
	    }
	}

	/**
	 * <pre>
	 * 파일 저장
	 * </pre>
	 * @param stream
	 * @param saveFile
	 */
	public static void saveFile(InputStream stream, File file) {
		try {
			logger.info("saveFile() file.getAbsolutePath()={}", file.getAbsolutePath());
	        FileUtil.makeDir(file.getParent());

            OutputStream bos = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            bos.close();
            stream.close();

        } catch (Exception e) {
            logger.error("fail in saveFile() file="+file, e);
        }
	}

}
