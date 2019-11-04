package com.smartmarmot.dbforbix.db.adapter;

import java.util.Set;

import com.smartmarmot.dbforbix.db.DBType;

public class DB_NOT_DEFINED extends AbstractDBAdapter {

	public DB_NOT_DEFINED(String name, String url, String user, String passwd,Integer maxactive, Integer maxidle,Integer maxwaitmillis, Integer queryTimeout, Set<String> set, Boolean pers) {
		this.name = name;
		this.url = url;
		this.user = user;
		this.passwd = passwd;
		this.maxactive = maxactive.intValue();
		this.maxidle = maxidle.intValue();
		this.maxwaitmillis=maxwaitmillis.intValue();
		this.queryTimeout = queryTimeout.intValue();
		this.persistence = pers;
		this.configurationUIDs=set;
	}
	


	@Override
	public DBType getType() {
		return DBType.DB_NOT_DEFINED;
	}

	

}
