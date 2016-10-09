package cn.itcast.lucene.index;

import java.io.File;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

public class LuceneQuick {

	@Test
	public void testLucene() throws Exception{
		//1.创建目录类，path是索引库的位置
		String path="F:\\indexs";
		FSDirectory directory = FSDirectory.open(new File(path));
		//2.创建分词器
		StandardAnalyzer analyzer = new StandardAnalyzer();
		//3.创建config
		IndexWriterConfig writerConfig = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
		//4.创建索引
		IndexWriter indexWriter=new IndexWriter(directory, writerConfig);
		//5.需要创建文档对象，索引库中存在的对象都是以文档的形式存在的
		Document doc=new Document();
		//6.文档中需要设置field,需要创建field
	
		//7.store.yes表示是否存储，表示是否存储到document
		doc.add(new StringField("id", "10010", Store.NO));
		doc.add(new TextField("title", "lucene入门级教程", Store.YES));
		doc.add(new TextField("content", "Lucene 是一个基于 Java 的全文信息检索工具包，它不是一个完整的搜索应用程序", Store.YES));
		//8.如果是no,表示不存在document中，但是依然具有索引
		
		indexWriter.addDocument(doc);
		//需要提交索引
		indexWriter.commit();
	}
}
