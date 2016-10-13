package com.lning.search.index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class SearchIndex {

	@Test
	public void searchIndex() throws Exception{
		//查询索引的步骤
		/**
		 * 1.读取索引文件
		 * 2.准备用户查询关键字
		 * 3.创建查询解析器解析查询字段QueryParser
		 * 4.创建索引解析类解析索引IndexSearch
		 * 5.使用索引查询类查询返回文档概要信息
		 * 6.使用索引查询文档
		 */
		//读取索引
		String path="F:\\indexs";
		DirectoryReader reader = DirectoryReader.open(FSDirectory.open(new File(path)));
		//创建查询索引类IndexSearcher
		IndexSearcher indexSearcher=new IndexSearcher(reader);
		//创建查询关键字
		String searchName="学习";
		//创建查询解析器
		QueryParser queryParser=new QueryParser("title", new IKAnalyzer());
		//使用查询解析器解析关键字
		Query query = queryParser.parse(searchName);
		//查询并且返回前十个文档的概要信息，还没有返回概要的内容
		TopDocs topDocs = indexSearcher.search(query, 10);
		//文档命中个数，也就是文档个数
		int totalHits = topDocs.totalHits;
		System.out.println("返回文档的个数"+totalHits);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		//目前返回两个文档
		for(ScoreDoc doc:scoreDocs){
			//文档id
			System.out.println("文档的id"+doc.doc);
			//文档的打分
			System.out.println("文档的打分"+doc.score);
			//查询文档的内容，根据文档id进行查询
			Document document = indexSearcher.doc(doc.doc);
			String id = document.get("id");
			String title = document.get("title");
			String content = document.get("content");
			System.out.println(id+"||"+title+"||"+content);
			
		}
		
	}
	
	@Test
	public void searchDocument() throws Exception{
		//查询索引的步骤
		/**
		 * 1.读取索引文件
		 * 2.准备用户查询关键字
		 * 3.创建查询解析器解析查询字段QueryParser
		 * 4.创建索引解析类解析索引IndexSearch
		 * 5.使用索引查询类查询返回文档概要信息
		 * 6.使用索引查询文档
		 */
		//读取索引
		String path="F:\\indexs";
		DirectoryReader reader = DirectoryReader.open(FSDirectory.open(new File(path)));
		//创建查询索引类IndexSearcher
		IndexSearcher indexSearcher=new IndexSearcher(reader);
		//创建查询关键字
		String searchName="ext.dic";
		//创建查询解析器
		QueryParser queryParser=new QueryParser("fileName", new IKAnalyzer());
		//使用查询解析器解析关键字
		Query query = queryParser.parse(searchName);
		//查询并且返回前十个文档的概要信息，还没有返回概要的内容
		TopDocs topDocs = indexSearcher.search(query, 10);
		//文档命中个数，也就是文档个数
		int totalHits = topDocs.totalHits;
		System.out.println("返回文档的个数"+totalHits);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		//目前返回两个文档
		for(ScoreDoc doc:scoreDocs){
			//文档id
			System.out.println("文档的id"+doc.doc);
			//文档的打分
			System.out.println("文档的打分"+doc.score);
			//查询文档的内容，根据文档id进行查询
			Document document = indexSearcher.doc(doc.doc);
			String id = document.get("fileName");
			String title = document.get("filePath");
			String content = document.get("fileContent");
			System.out.println(id+"||"+title+"||"+content);
			
		}
		
	}
}
