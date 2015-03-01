package org.tww.compress;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;
import org.apache.tools.tar.TarOutputStream;
/**
 * tar.gz,tar文件压缩、解压类
 * @author GuoYun
 * @version 1.0
 * @date 2012-04-05
 *
 */
public class ZipUtil {
	 /**  
     * 解压tar.gz 文件  
     * @param file 要解压的tar.gz文件对象  
     * @param outputDir 要解压到某个指定的目录下  
     * @throws IOException  
     */  
    public static void unTarGz(File file,String outputDir) throws IOException{   
        TarInputStream tarIn = null;   
        try{   
            tarIn = new TarInputStream(new GZIPInputStream(   
                    new BufferedInputStream(new FileInputStream(file))),1024 * 2);   
            createDirectory(outputDir,null);//创建输出目录   
            TarEntry entry = null;   
            while( (entry = tarIn.getNextEntry()) != null ){   
                if(entry.isDirectory()){//是目录   
                    createDirectory(outputDir,entry.getName());//创建空目录   
                }else{//是文件   
                    File tmpFile = new File(outputDir + "/" + entry.getName());   
                    createDirectory(tmpFile.getParent() + "/",null);//创建输出目录   
                    OutputStream out = null;   
                    try{   
                        out = new FileOutputStream(tmpFile);   
                        int length = 0;   
                        byte[] b = new byte[2048];   
                        while((length = tarIn.read(b)) != -1){   
                            out.write(b, 0, length);   
                        }   
                    }catch(IOException ex){   
                        throw ex;   
                    }finally{   
                        if(out != null)   
                            out.close();   
                    }   
                }   
            }   
        }catch(IOException ex){   
            throw new IOException("解压归档文件"+outputDir+"/"+file.getName()+"出现异常",ex);   
        } finally{   
            try{   
                if(tarIn != null){   
                    tarIn.close();   
                }   
            }catch(IOException ex){   
                throw new IOException("关闭tarFile出现异常",ex);   
            }   
        }
    }
           
    /**  
     * 解压tar文件  
     * @param file 要解压的tar文件对象  
     * @param outputDir 要解压到某个指定的目录下  
     * @throws IOException  
     */ 
	 public static void unTar(File file,String outputDir) throws IOException {   
	      TarInputStream tarIn = null;   
	      try{   
	            tarIn = new TarInputStream(new FileInputStream(file),1024 * 2); 
	            createDirectory(outputDir,null);//创建输出目录   
	            TarEntry entry = null;   
	           while( (entry = tarIn.getNextEntry()) != null ){   
	                if(entry.isDirectory()){//是目录   
	                    createDirectory(outputDir,entry.getName());//创建空目录   
	                }else{//是文件   
	                    File tmpFile = new File(outputDir + "/" + entry.getName());   
	                    createDirectory(tmpFile.getParent() + "/",null);//创建输出目录   
	                    OutputStream out = null;   
	                    try{   
	                        out = new FileOutputStream(tmpFile);   
	                        int length = 0;   
	                        byte[] b = new byte[2048];   
	                        while((length = tarIn.read(b)) != -1){   
	                            out.write(b, 0, length);   
	                        }   
	                    }catch(IOException ex){   
	                        throw ex;   
	                    }finally{   
	                        if(out != null)   
	                            out.close();   
	                    }   
	                }   
	            }   
	        }catch(IOException ex){   
	            throw new IOException("解压归档文件"+outputDir+"/"+file.getName()+"出现异常",ex);   
	        } finally{   
	            try{   
	                if(tarIn != null){   
	                    tarIn.close();   
	                }   
	            }catch(IOException ex){   
	                throw new IOException("关闭tarFile出现异常",ex);   
	            }   
	        }   
	 }
	 
	   /**  
	     * 构建目录  
	     * @param outputDir  
	     * @param subDir  
	     */  
	    public static void createDirectory(String outputDir,String subDir){   
	        File file = new File(outputDir);   
	        if(!(subDir == null || subDir.trim().equals(""))){//子目录不为空   
	            file = new File(outputDir + "/" + subDir);   
	        }   
	        if(!file.exists()){   
	            file.mkdirs();   
	        }   
	           
	    } 
	    
	    /**  
	     * 清理文件(目录或文件)  
	     * @param file  
	     */  
	    public static void deleteDirectory(File file){   
	        if(file.isFile()){   
	            file.delete();//清理文件   
	        }else{   
	            File list[] = file.listFiles();   
	            if(list != null){   
	                for(File f : list){   
	                    deleteDirectory(f);   
	                }   
	                file.delete();//清理目录   
	            }   
	        }   
	    }
	    
	    /**  
	     * 压缩归档tar文件  
	     * @param file 归档的文件对象  
	     * @param out 输出tar流  
	     * @param dir 相对父目录名称  
	     * @param boo 是否把空目录归档进去  
	     */  
	    public static void tar(File file,TarOutputStream out,String dir,boolean boo) throws IOException{   
	        if(file.isDirectory()){//是目录   
	            File []listFile = file.listFiles();//得出目录下所有的文件对象   
	            if(listFile.length == 0 && boo){//空目录归档   
	                out.putNextEntry(new TarEntry(dir + file.getName() + "/"));//将实体放入输出Tar流中   
	                System.out.println("归档." + dir + file.getName() + "/");   
	                return;   
	            }else{   
	                for(File cfile : listFile){   
	                    tar(cfile,out,dir + file.getName() + "/",boo);//递归归档   
	                }   
	            }   
	        }else if(file.isFile()){//是文件   
	            System.out.println("归档." + dir + file.getName() + "/");   
	            byte[] bt = new byte[2048*2];   
	            TarEntry ze = new TarEntry(dir+file.getName());//构建tar实体   
	            //设置压缩前的文件大小   
	            ze.setSize(file.length());   
	            //ze.setName(file.getName());//设置实体名称.使用默认名称   
	            out.putNextEntry(ze);////将实体放入输出Tar流中   
	            FileInputStream fis = null;   
	            try{   
	                fis = new FileInputStream(file);   
	                int i = 0;   
	                while((i = fis.read(bt)) != -1) {//循环读出并写入输出Tar流中   
	                    out.write(bt, 0, i);   
	                }   
	            }catch(IOException ex){   
	                throw new IOException("写入归档文件出现异常",ex);   
	            }finally{   
	                try{   
	                    if (fis != null)   
	                        fis.close();//关闭输入流   
	                    out.closeEntry();   
	                }catch(IOException ex){   
	                    throw new IOException("关闭输入流出现异常");   
	                }   
	            }              
	        }   
	    }   
	    /**
	     * 压缩归档tar.gz文件   
	     * @param tarFile 要归档的tar文件
	     * @param tarGzPath 将要生成的.gz压缩文件的存放路径，例如：C:/GZ
	     */
	    public void tarGz(File tarFile,String tarGzPath){   
	        File gzFile = new File(tarGzPath + "/" + tarFile.getName() + ".gz");//将要生成的压缩文件   
	        GZIPOutputStream out = null;   
	        InputStream in = null;   
	        boolean boo = false;//是否成功   
	        try{   
	            in = new FileInputStream(tarFile);   
	            out = new GZIPOutputStream(new FileOutputStream(gzFile),1024 * 2);   
	            byte b[] = new byte[1024 * 2];   
	            int length = 0;   
	            while( (length = in.read(b)) != -1 ){   
	                out.write(b,0,length);   
	            }   
	            boo = true;   
	        }catch(Exception ex){   
	            throw new RuntimeException("压缩归档文件失败",ex);   
	        }finally{   
	            try{   
	                if(out!=null)   
	                    out.close();   
	                if(in!=null)   
	                    in.close();   
	            }catch(IOException ex){   
	                throw new RuntimeException("关闭流出现异常",ex);   
	            }finally{   
	                if(!boo){//清理操作   
	                    tarFile.delete();   
	                    if(gzFile.exists())   
	                        gzFile.delete();   
	                }   
	            }   
	        }   
	    }   
	    /**
		 * 将gzip压缩的数据读到内存中
		 * @param file
		 * @param outputDir
		 */
		public static byte[] getGZipBytes(File file) {
			FileInputStream fin = null;
			GZIPInputStream gzin = null;
//				FileOutputStream fout = new FileOutputStream(outputDir + "/" + fileName.replace(".zip", ""));
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try {
				 fin = new FileInputStream(file);
				 gzin = new GZIPInputStream(fin);
				byte[] buf = new byte[1024];
				int num;
				while((num=gzin.read(buf, 0, buf.length)) != -1) {
//					fout.write(buf, 0, num);
					bos.write(buf, 0, num);
				}
				bos.flush();
				bos.close();
				gzin.close();
//				fout.close();
				fin.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			return bos.toByteArray();
		}
		/**
		 * 读取 bzip2数据为byte数组
		 * @param fileName
		 * @return byte[]
		 */
		public static byte[] unBZip2(String fileName) {
			FileInputStream fin;
			byte[] buffer = null;
			byte[] rtnBytes = null;
			try {
				fin = new FileInputStream(fileName);
				BufferedInputStream in = new BufferedInputStream(fin);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(in);
				buffer = new byte[1024];
				int n = 0;
				while (-1 != (n = bzIn.read(buffer))) {    
					baos.write(buffer, 0, n);
				}
				rtnBytes = baos.toByteArray();
				bzIn.close();				
				in.close();
				fin.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return rtnBytes;
		}
		
		/**
		 * 读取 bzip2数据为byte数组
		 * @param fileName
		 * @return byte[]
		 */
		public static byte[] unBZip2(byte[] bytes) {
			byte[] buffer = null;
			byte[] rtnBytes = null;
			BufferedInputStream in = null;
			ByteArrayOutputStream baos = null;
			BZip2CompressorInputStream bzIn = null;
			try {
				in = new BufferedInputStream(new ByteArrayInputStream(bytes));
				baos = new ByteArrayOutputStream();
				bzIn = new BZip2CompressorInputStream(in);
				buffer = new byte[1024];
				int n = 0;
				while (-1 != (n = bzIn.read(buffer))) {    
					baos.write(buffer, 0, n);
				}
				rtnBytes = baos.toByteArray();
				baos.flush();						
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					if (null != bzIn){
						bzIn.close();
					}
					if (null != baos){
						baos.close();
					}
					if (null != in){
						in.close();
					}					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return rtnBytes;
		}
}
