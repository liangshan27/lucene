package cn.itcast.lucene.analyse;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.cn.ChineseAnalyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class Analyses {

	@Test
	public void testAnalyse() throws Exception{
		//1.创建一个基本分词器
		//标准分词器
		//Analyzer analyzer = new StandardAnalyzer();
		//cjk分词器把两个词语组成一组
		//CJKAnalyzer analyzer = new CJKAnalyzer();
		//Analyzer analyzer=new ChineseAnalyzer();
		//Analyzer analyzer = new SmartChineseAnalyzer();
		//IK分词器
		IKAnalyzer analyzer = new IKAnalyzer();
		//使用ik分词器的步骤
		/*
		 * 1.导入扩展分词和停止分词
		 * 2.导入分词器的配置文件，需要指定配置扩展词字典和停止词字典
		 */
		//2.使用TokenStream测试分词效果
		TokenStream tokenStream = analyzer.tokenStream("title", "凤姐在美国洗脚!lucene");
		//3.指针指向开始位置
		tokenStream.reset();
		
		//5.设置关键词的引用
		CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
		//6.设置偏移量的引用
		OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
		//7.便利汇总列表tokenstream
		//8.increamenttoken判断列表是否恢复，相当于jdbc的resultset中的next
		while(tokenStream.incrementToken()){
			//输出开始位置
			System.out.println("开始位置:"+offsetAttribute.startOffset());
			//输出关键词
			System.out.println("关键词:  "+charTermAttribute);
			//输出结束为
			System.out.println("结束位置:"+offsetAttribute.endOffset());
		}
		
	}
}
