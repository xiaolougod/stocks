package com.lou.crawlerCorrelation.arithmetic.BestFirst;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityIndex;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;
import com.sleepycat.persist.StoreConfig;

/**
 * @author loukangwei
 * @typename Best_First
 * @desc 利用breakdb实现url基于socred的的优先级遍历
 * @desc je-5.0.73.jar为breakdb架包
 */
public class Best_First {

	EntityStore store;
	PrimaryIndex<String, NewsSourceEntity> newsByURL;
	SecondaryIndex<Integer, String, NewsSourceEntity> secondaryIndex;

	/**
	 * @authorloukangwei
	 * @date2016年5月26日 下午3:00:44
	 * @returnTypevoid
	 * @desc构建todo列表
	 */
	public void ToDoTaskList(Environment env) throws Exception {
		StoreConfig storeConfig = new StoreConfig();
		storeConfig.setAllowCreate(true);
		storeConfig.setTransactional(false);
		store = new EntityStore(env, "classDb", storeConfig);
		newsByURL = store.getPrimaryIndex(String.class, NewsSourceEntity.class);
		secondaryIndex = store.getSecondaryIndex(this.newsByURL, Integer.class, "score");
	}

	/**
	 * @authorloukangwei
	 * @date2016年5月26日 下午3:01:13
	 * @returnTypeNewsSource
	 * @desc从todo列表中获取最大分值的url
	 */
	public NewsSourceEntity removeBest() throws DatabaseException {
		Integer score = secondaryIndex.sortedMap().lastKey();
		if (score != null) {
			EntityIndex<String, NewsSourceEntity> urlList = secondaryIndex.subIndex(score);
			EntityCursor<String> ec = urlList.keys();
			String url = ec.first();
			ec.close();
			NewsSourceEntity source = urlList.get(url);
			urlList.delete(url);
			return source;
		}
		return null;
	}
}
