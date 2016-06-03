package com.lou.crawlerCorrelation.arithmetic.BestFirst;

import java.io.File;
import java.util.SortedMap;

import com.sleepycat.bind.serial.ClassCatalog;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.collections.StoredSortedMap;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

public class BerkeleyDBUtil {

	public BerkeleyDBUtil() {
		String dir = "";

		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setAllowCreate(true);// 允许创建表
		envConfig.setTransactional(false);// 不允许使用事务

		Environment env = new Environment(new File(dir), envConfig);

		// 使用一个通用数据库配置
		DatabaseConfig dbConfig = new DatabaseConfig();

		dbConfig.setAllowCreate(true);
		dbConfig.setTransactionalVoid(false);

		// 如果有序列化綁定则需要类别数据库
		Database catalogDb = env.openDatabase(null, "catalog", dbConfig);
		ClassCatalog catalog = new StoredClassCatalog(catalogDb);

		// 关键字数据类型是字符串
		TupleBinding<String> keyBinding = TupleBinding.getPrimitiveBinding(String.class);
		// 值数据类型也是字符串
		SerialBinding<String> dataBinding = new SerialBinding<String>(catalog, String.class);

		Database db = env.openDatabase(null, "url", dbConfig);

		// 创建一个映射
		SortedMap<String, String> map = new StoredSortedMap<String, String>(db, keyBinding, dataBinding, true);
		// 把抓取的url地址作为关键字放入映射
		String url = "url";
		map.put(url, null);
		if (map.containsKey(url)) {
			System.out.print("抓取");
		}

	}
}
