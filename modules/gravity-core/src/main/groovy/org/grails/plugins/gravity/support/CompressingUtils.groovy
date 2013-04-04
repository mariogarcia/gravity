package org.grails.plugins.gravity.support

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.apache.commons.io.FilenameUtils
import org.apache.commons.compress.archivers.zip.ZipFile


/**
 * This class wraps all uncompressing functionalities
**/
class CompressingUtils{

	/** 
	 * This method uncompresses the zip file passed as parameter 
	 * 
	 * @param file
	 * @return the directory uncompressed
	 **/
	def uncompressZip(file){
		def zipFile = new ZipFile(file)
		def simpleName = FilenameUtils.getBaseName(file.name)
		def result  = new File(file.parentFile,simpleName)
	 /* Collecting every file inside the zip */
		zipFile.entries.each{entry->
			def is = zipFile.getInputStream(entry)
			def of = new File(result,entry.name)
				of.parentFile.mkdirs()
			def os = new FileOutputStream(of)
			try{
				os << is
			} catch (e){
				[is,os]*.close()	
			}	
		}
		result	
	}

	/**
	 * This method uncompresses the tar file passed as parameter
	 *
	 * @param file
	 * @return the parent directory where all uncompressed content is
	**/
	def uncompressTar(file){
	 /* Getting metadata */
		def simpleName = FilenameUtils.getBaseName(file.name)
		def result  = new File(file.parentFile,simpleName)
		try{
			 /* Collecting entries */
				def is = new TarArchiveInputStream(new FileInputStream(file))	
				def entry = is.getNextEntry()
			 /* Looping through the tar entries */
				while(entry){
				 /* Creating every entry relative to the parent directory */
					def nextEntry = new File(result,entry.name)
					def output = new FileOutputStream(nextEntry)
					try{
						 /* Copying content to the uncompressed file entry */
							while (readableData > 0){
								def readableData = entry.getSize()
								if (readableData){
									def content = new byte[readableData]
								 /* Reading */
									is.read(content,offset,content.length - offset)
								 /* Writing */
									output.write(content)
								}
							}
					} catch (ie){
						output.close()
						throw Exception(ie)
					}
					entry = is.getNextEntry()
				}
		} catch (e){
			is.close()
			throw new Exception("Exception while uncompressing ${file?.name} [${e.message}]")
		}
		result
	}

	def uncompressGzipFile(file){
		FileInputStream fin = new FileInputStream("archive.tar.gz");
		BufferedInputStream ins = new BufferedInputStream(fin);
		FileOutputStream out = new FileOutputStream("archive.tar");
		GzipCompressorInputStream gzIn = new GzipCompressorInputStream(ins);
		final byte[] buffer = new byte[buffersize];
		int n = 0;
		while (-1 != (n = gzIn.read(buffer))) {
			out.write(buffer, 0, n);
		}
		out.close();
		gzIn.close();
	}

}

