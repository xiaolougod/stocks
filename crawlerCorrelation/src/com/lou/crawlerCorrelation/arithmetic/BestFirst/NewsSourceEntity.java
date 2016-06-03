package com.lou.crawlerCorrelation.arithmetic.BestFirst;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.Relationship;
import com.sleepycat.persist.model.SecondaryKey;

@Entity
public class NewsSourceEntity {
	@PrimaryKey
	public String url;
	public String source;
	public String level;
	public int rank;
	public String urlDesc = null;
	@SecondaryKey(relate = Relationship.MANY_TO_ONE)
	public int score;
}
