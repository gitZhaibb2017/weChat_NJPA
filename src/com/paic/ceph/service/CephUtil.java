package com.paic.ceph.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.omg.CORBA_2_3.portable.InputStream;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.StringUtils;
import com.sun.corba.se.spi.orbutil.fsm.Input;

/**
 * 对接ceph工具类
 *
 */
public class CephUtil 
{
	private static final Logger LOGGER = Logger.getLogger(CephUtil.class);
	
	private static String ACCESS_KEY =   "AWR660YFOHJGZOHWVU8M";
	
	private static String SECRET_KEY =   "yankunli";
	
	private static String ENDPOINT = "localhost:7480"; 
//	private static String ENDPOINT = "cephapp.iask.in:7480"; 
	
	private static String BUCKET_NAME = "first_bucket";
	
	public static String FILE_SPLIT = "\\|\\|";
	
	
	/**
	 * 根据accessKey、secretKey获取AmazonS3
	 * 
	 * @param accessKey s3凭证
	 * @param secretKey s3秘钥
	 * @return
	 */
	public static AmazonS3 getAmazonS3(String accessKey, String secretKey ){
	
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
	
		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setProtocol(Protocol.HTTP);
		clientConfig.setMaxConnections(100);
//		clientConfig.setMaxErrorRetry(3);
		clientConfig.setConnectionTimeout(5*1000);
		clientConfig.setSocketTimeout(5*1000);
//		clientConfig.setSignerOverride("S3SignerType");
//		clientConfig.useGzip();
//		clientConfig.useTcpKeepAlive();
	
		AmazonS3 conn = new AmazonS3Client(credentials, clientConfig);
		conn.setEndpoint(ENDPOINT);
		return conn;
	}
	
	
	/**
	 * 根据bucketName，获取Bucket。如果不存在，根据type判定是否重新创建(type为1表示创建)
	 * 
	 * @param conn
	 * @param bucketName
	 * @param type
	 * @return
	 */
	public static Bucket getBucketByName(AmazonS3 conn,String bucketName,int type){
		
		Bucket bucket = null;
		List<Bucket> buckets = conn.listBuckets();
		for (Bucket b : buckets) {
			if(bucketName.equals(b.getName())){
				bucket = b;
				break;
			}
		}
		
		if(bucket == null && type == 1){
			bucket = conn.createBucket(bucketName);
		}
		
		return bucket;
	}
	
	
	/**
	 * 打印bucket中有的内容
	 * 
	 * @param conn
	 * @param bucketName
	 */
	public static void listObjectsByBucketName(AmazonS3 conn,String bucketName){
		ObjectListing objects = conn.listObjects(bucketName);
        do {
                for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
                        System.out.println(objectSummary.getKey() + "\t" +
                        		objectSummary.getSize() + "\t" +
                                StringUtils.fromDate(objectSummary.getLastModified()));
                }
                objects = conn.listNextBatchOfObjects(objects);
        } while (objects.isTruncated());
	}
	
	
	
//	public static void putObjectContent(InputStream input){
////		dd
//		AmazonS3 conn =  getAmazonS3(ACCESS_KEY,SECRET_KEY);
//		Bucket bucket = getBucketByName(conn,BUCKET_NAME,0);
//		putObject(conn,bucket, input);
//		 
//		return BUCKET_NAME+"||"+file.getName();
//	}
	
	/**
	 * 上传文件到s3
	 * 
	 * @param conn
	 * @param bucket
	 * @param input
	 * @param imgName
	 */
	public static void putObject(AmazonS3 conn,Bucket bucket,File file){
		conn.putObject(bucket.getName(), file.getName(), file);
	}
	
	
//	public static void putObject(AmazonS3 conn,Bucket bucket,String fileName,InputStream input){
//		conn.putObject(arg0, arg1, arg2, arg3)
//	}
	
	/**
	 * 从s3下载文件
	 * 
	 * @param conn
	 * @param bucket
	 * @param imgName
	 * @param storeBasePath
	 */
	public static void getObject(AmazonS3 conn,Bucket bucket,String imgName,String storeBasePath){
		
		
		conn.getObject(new GetObjectRequest(bucket.getName(), imgName),new File(storeBasePath+imgName));
		
		//TODO:直接得到文件对象
	}
	
	
    /** 
   * @Title: getContentFromS3 
   * @Description: 获取文件2进制流
   * @param  bucketName
   * @param  remoteFileName
   * @param  IOException    设定文件 
   * @return S3ObjectInputStream    返回类型  数据流
   * @throws 
   */
//   public static S3ObjectInputStream getContentFromS3(AmazonS3 conn,String bucketName,String remoteFileName) throws IOException {
	public static byte[] getContentFromS3(String bucketName,String remoteFileName) throws IOException {
		try {
			AmazonS3 conn = getAmazonS3(ACCESS_KEY, SECRET_KEY);
			GetObjectRequest request  = new GetObjectRequest(bucketName,remoteFileName);
			S3Object object = conn.getObject(request);
			S3ObjectInputStream inputStream = object.getObjectContent();
			return getObjectContent(inputStream);
		} catch (Exception e) {
			LOGGER.error("从s3下载文件异常", e);
		} 
		return null;
    }
	
	/**
	 * 
	 * @param inputStream
	 * @return
	 */
	private static byte[] getObjectContent(S3ObjectInputStream inputStream){
		
		byte[] data = null;
		
		try {
			ByteArrayOutputStream  outPut = new ByteArrayOutputStream();
			byte[] buf  =new byte[1024];
			int num = 0;
			
			while( (num = inputStream.read(buf) )!= -1 ){
				outPut.write(buf,0,num);
			}
			data = outPut.toByteArray();
			outPut.close();
			inputStream.close();
		} catch (IOException e) {
			LOGGER.error("getObjectContent异常", e);
		}
		return data;
	}
	
	/**
	 * 从s3删除文件
	 * 
	 * @param conn
	 * @param bucket
	 * @param imgName
	 */
	public static void delObject(AmazonS3 conn,Bucket bucket,String imgName){
		conn.deleteObject(bucket.getName(), imgName);
	}
	
	/**
	 * 修改存放在s3处文件的访问权限
	 * 
	 * @param conn
	 * @param bucket
	 * @param imgName
	 * @param cannedAccessControlList
	 */
	public static void changeObjectACL(AmazonS3 conn,Bucket bucket,String imgName,CannedAccessControlList cannedAccessControlList){
		conn.setObjectAcl(bucket.getName(), imgName, cannedAccessControlList);
	}
	
	
//	public static void main(String[] args) {
//		File file = new File("E:\\test1.jpg");
//		System.out.println(file.length());
//	}
	
	
	public static String uploadCephPic(String imgUri){
		
		AmazonS3 conn =  getAmazonS3(ACCESS_KEY,SECRET_KEY);
		Bucket bucket = getBucketByName(conn,BUCKET_NAME,0);
		File file = new File(imgUri);
		putObject(conn,bucket,file);
		 
		return BUCKET_NAME+"||"+file.getName();
	}
	
    public static void main( String[] args ) throws FileNotFoundException
    {
    	
        
        AmazonS3 conn =  getAmazonS3(ACCESS_KEY,SECRET_KEY);
       
        
        Bucket bucket = getBucketByName(conn,BUCKET_NAME,0);
        
        File file = new File("E:\\ttt.jpg");
        
        
//        InputStream input = new FileInputStream(file);
        putObject(conn,bucket,file);
      
//        HttpURLConnection httpConn;
//		try {
//			
//			String accessToken ="3RAQFHnIn6uKv2v9Y-iADuDy4v2fZGa-194AK9nOvPiK9zWP4DTqGFNm3f45qSRyJMrvV5KggNHbCpP3_60OPn22JKQitOTaGwT5-E2cueYBmngB68aa2H53PUqAeULZWZLaAAAZCO";
//			
//			String mediaId  ="X9eLoFUCZUdLlAg-FaNfyQpdXnTgZoZCVAqner7zmG8fQUqrSeiRkrHtNo0xDkVw";
//			httpConn = ImageHelper.downloadMedia(accessToken, mediaId);
//			InputStream input = httpConn.getInputStream();
//			putObject(conn,bucket,input,"1490259525.jpg");
//			input.close();
//			httpConn.disconnect();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
       
        
        System.out.println("++++before");
        
//        listObjectsByBucketName(conn,bucket.getName());
        listObjectsByBucketName(conn,"first_bucket");//first_bucket
        
        System.out.println("++++after");
        
//        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket.getName(), "secret_plans.txt");
//        System.out.println(conn.generatePresignedUrl(request));
        
    }
}
