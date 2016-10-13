package com.lning.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.wltea.analyzer.lucene.IKAnalyzer;


public class LuceneUtils {

	private static Directory directory;
	private static Analyzer analyzer;
	private static IndexWriter indexWriter;
	private static String luceneDir;
	//懒汉式，创建indexwriter
	static{
		try {
			InputStream inputStream = LuceneUtils.class.getClassLoader().getResourceAsStream("lucene.properties");
			Properties properties=new Properties();
			properties.load(inputStream);
			luceneDir=properties.getProperty("lucene_dir");//文件索引目录
			directory=FSDirectory.open(new File(luceneDir));
			analyzer=new IKAnalyzer();
			IndexWriterConfig writerConfig=new IndexWriterConfig(Version.LATEST, analyzer);
			indexWriter=new IndexWriter(directory, writerConfig);
			//虚拟机闪退时关闭
			Runtime.getRuntime().addShutdownHook(new Thread(){
				@Override
				public void run() {
					try {
						indexWriter.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	//获取indexwriter
	public static IndexWriter getIndexWriter(){
		return indexWriter;
	}
	//获取indexsearch
	public static IndexSearcher getIndexSearch() throws Exception{
		return new IndexSearcher(DirectoryReader.open(directory));
	}
	//打印控制台
	public static void writeDocument(Query query) throws Exception{
		//创建查询器
		IndexSearcher indexSearcher=new IndexSearcher(DirectoryReader.open(FSDirectory.open(new File(luceneDir))));
		//搜索前n名结果：按照得分排序的结果
		TopDocs docs = indexSearcher.search(query, Integer.MAX_VALUE);
		//打印结果集
		System.out.println("搜索结果"+docs.totalHits);
		for(ScoreDoc scoreDoc:docs.scoreDocs){
			System.out.println("得分："+scoreDoc.score);
			Document doc = indexSearcher.doc(scoreDoc.doc);
			System.out.println("id"+doc.get("id"));
			System.out.println("标题"+doc.get("tilte"));
			System.out.println("内容"+doc.get("content"));
			System.out.println();
		}
	}
	
	public static List<Document> fileToDocument(String path) throws Exception{
		//创建document的集合
		List<Document> dList=new ArrayList<Document>();
		//获取文件
		File folder=new File(path);
		if(!folder.isDirectory()){
			return null;
		}
		
		//获取目录中的所有文件
		File[] files = folder.listFiles();
		for(File file:files){
			//文件名称
			String fileName = file.getName();
			String filePath = file.getPath();
			//使用tika来获取文件内容
			Tika tika = new Tika();
			String fileContent = tika.parseToString(file);
			
			//创建文档对象
			Document docment=new Document();
			docment.add(new StringField("fileName", fileName, Store.YES));
			docment.add(new StringField("filePath", filePath, Store.YES));
			docment.add(new TextField("fileContent", fileContent, Store.YES));
			indexWriter.addDocument(docment);
		}
		
		return dList;
	}
}










