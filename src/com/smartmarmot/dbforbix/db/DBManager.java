/*
 * This file is part of DBforBix.
 *
 * DBforBix is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * DBforBix is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * DBforBix. If not, see <http://www.gnu.org/licenses/>.
 */

package com.smartmarmot.dbforbix.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.smartmarmot.dbforbix.config.Database;
import com.smartmarmot.dbforbix.db.adapter.ALLBASE;
import com.smartmarmot.dbforbix.db.adapter.DBAdapter;
import com.smartmarmot.dbforbix.db.adapter.DB2;
import com.smartmarmot.dbforbix.db.adapter.DB_NOT_DEFINED;
import com.smartmarmot.dbforbix.db.adapter.MSSQL;
import com.smartmarmot.dbforbix.db.adapter.MySQL;
import com.smartmarmot.dbforbix.db.adapter.Oracle;
import com.smartmarmot.dbforbix.db.adapter.PGSQL;
import com.smartmarmot.dbforbix.db.adapter.SQLANY;
import com.smartmarmot.dbforbix.db.adapter.SYBASE;

public class DBManager {

	private static DBManager	instance;

	protected DBManager() {}

	private Set<DBAdapter>	databases	= new HashSet<>();

	public static DBManager getInstance() {
		if (instance == null)
			instance = new DBManager();
		return instance;
	}
	
//DB2, ORACLE, MSSQL, MYSQL, PGSQL, ALLBASE, SYBASE, SQLANY;
	public void addDatabase(Database cfg) {
		if(databases.contains(cfg.getDBNameFC()))
			return;
		switch (cfg.getType()) {
			case DB2:
				databases.add(new DB2(cfg.getDBNameFC(), cfg.getURL(), cfg.getUser(), cfg.getPassword(), cfg.getMaxActive(),cfg.getMaxIdle()
						,cfg.getMaxWaitMillis(),cfg.getQueryTimeout(),cfg.getConfigurationUIDs(),cfg.getPersistence()));
			break;
			case ORACLE:
				databases.add(new Oracle(cfg.getDBNameFC(), cfg.getURL(), cfg.getUser(), cfg.getPassword(),cfg.getMaxActive(),cfg.getMaxIdle()
						,cfg.getMaxWaitMillis(),cfg.getQueryTimeout(),cfg.getConfigurationUIDs(),cfg.getPersistence()));
			break;
			case MSSQL:
				databases.add(new MSSQL(cfg.getDBNameFC(), cfg.getURL(), cfg.getUser(), cfg.getPassword(),cfg.getMaxActive(),cfg.getMaxIdle()
						,cfg.getMaxWaitMillis(),cfg.getQueryTimeout(),cfg.getConfigurationUIDs(),cfg.getPersistence()));
			break;
			case MYSQL:
				databases.add(new MySQL(cfg.getDBNameFC(), cfg.getURL(), cfg.getUser(), cfg.getPassword(),cfg.getMaxActive(),cfg.getMaxIdle()
						,cfg.getMaxWaitMillis(),cfg.getQueryTimeout(),cfg.getConfigurationUIDs(),cfg.getPersistence()));
			break;
			case PGSQL:
				databases.add(new PGSQL(cfg.getDBNameFC(), cfg.getURL(), cfg.getUser(), cfg.getPassword(),cfg.getMaxActive(),cfg.getMaxIdle()
						,cfg.getMaxWaitMillis(),cfg.getQueryTimeout(),cfg.getConfigurationUIDs(),cfg.getPersistence()));
			break;
			case ALLBASE:
				databases.add(new ALLBASE(cfg.getDBNameFC(), cfg.getURL(), cfg.getUser(), cfg.getPassword(),cfg.getMaxActive(),cfg.getMaxIdle()
						,cfg.getMaxWaitMillis(),cfg.getQueryTimeout(),cfg.getConfigurationUIDs(),cfg.getPersistence()));
			break;
			case SYBASE:
				databases.add(new SYBASE(cfg.getDBNameFC(), cfg.getURL(), cfg.getUser(), cfg.getPassword(),cfg.getMaxActive(),cfg.getMaxIdle()
						,cfg.getMaxWaitMillis(),cfg.getQueryTimeout(),cfg.getConfigurationUIDs(),cfg.getPersistence()));
			break;
			case SQLANY:
				databases.add(new SQLANY(cfg.getDBNameFC(), cfg.getURL(), cfg.getUser(), cfg.getPassword(),cfg.getMaxActive(),cfg.getMaxIdle()
						,cfg.getMaxWaitMillis(),cfg.getQueryTimeout(),cfg.getConfigurationUIDs(),cfg.getPersistence()));
			break;
			case DB_NOT_DEFINED:
				databases.add(new DB_NOT_DEFINED(cfg.getDBNameFC(), cfg.getURL(), cfg.getUser(), cfg.getPassword(),cfg.getMaxActive(),cfg.getMaxIdle()
						,cfg.getMaxWaitMillis(),cfg.getQueryTimeout(),cfg.getConfigurationUIDs(),cfg.getPersistence()));
			break;
		}
	}

	/**
	 * Get databases by configurationUID
	 * @param configurationUID
	 * @return array of DBs
	 */
	public DBAdapter[] getDBsByConfigurationUID(String configurationUID) {
		ArrayList<DBAdapter> result = new ArrayList<DBAdapter>(databases.size());
		for (DBAdapter db : databases) {
			if (db.getConfigurationUIDs().contains(configurationUID))
				result.add(db);
		}
		return result.toArray(new DBAdapter[result.size()]);
	}

	public DBAdapter[] getDatabases() {
		return databases.toArray(new DBAdapter[databases.size()]);
	}
	
	public DBManager cleanAll() {
		Set<String> configurationUIDs=new HashSet<>();		
		for(DBAdapter db:getDatabases()){
			configurationUIDs.addAll(db.getConfigurationUIDs());
		}				
		return clean(configurationUIDs);
	}
	
	public DBManager clean(Collection<String> configurationUIDs) {
		if(!configurationUIDs.isEmpty()){
			for(DBAdapter db:getDatabases()){
				db.getConfigurationUIDs().removeAll(configurationUIDs);
				if(db.getConfigurationUIDs().isEmpty())
					db.abort();
			}
			java.util.function.Predicate<DBAdapter> dbPredicate=(DBAdapter db)-> db.getConfigurationUIDs().isEmpty();
			databases.removeIf(dbPredicate);
			if(databases.isEmpty())
				instance=null;
		}
		return getInstance();
	}
	
	
	public DBAdapter getDatabaseByName(String dbNameFC) {
		DBAdapter result=null;
		for(DBAdapter db:databases){
			if(db.getName().equals(dbNameFC)){
				result=db;
				break;
			}
		}
		return result;
	}
	
}
