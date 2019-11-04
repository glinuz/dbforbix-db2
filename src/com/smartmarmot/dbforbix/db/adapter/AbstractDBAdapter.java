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

package com.smartmarmot.dbforbix.db.adapter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.dbcp2.cpdsadapter.DriverAdapterCPDS;
import org.apache.commons.dbcp2.datasources.SharedPoolDataSource;
import org.apache.log4j.Logger;

import com.smartmarmot.dbforbix.config.Config;
import com.smartmarmot.dbforbix.db.DBType;

abstract class AbstractDBAdapter implements DBAdapter {

	private static final Logger		LOG	= Logger.getLogger(AbstractDBAdapter.class);

	private SharedPoolDataSource	datasrc;
	
	protected String name;
	protected String url;
	protected String user;
	protected String passwd;
	protected int 	 maxactive;
	protected int    maxidle;
	protected int    maxwaitmillis;
	protected int	 queryTimeout;


	protected boolean persistence;
	protected Set<String> configurationUIDs=new HashSet<String>();

	public Set<String> getConfigurationUIDs() {
		return configurationUIDs;
	}

	public void addConfigurationUID(String configurationUID) {
		configurationUIDs.add(configurationUID);
	}


	private void createConnection() throws SQLException, ClassNotFoundException{
			LOG.info("Creating new connection pool for database " + getName());
			Config cfg=Config.getInstance();
			DriverAdapterCPDS cpds = new DriverAdapterCPDS();
			cpds.setDriver(getType().getJDBCDriverClass());
			cpds.setUrl(getURL());
			cpds.setUser(getUser());
			cpds.setPassword(getPassword());
			datasrc = new SharedPoolDataSource();
			datasrc.setConnectionPoolDataSource(cpds);
			datasrc.setLoginTimeout(cfg.getLoginTimeout());
			datasrc.setMaxTotal(getMaxActive());
			datasrc.setDefaultMaxIdle(getMaxIdle());
			datasrc.setDefaultMaxWaitMillis(getMaxWaitMillis());
			datasrc.setValidationQuery(getType().getAliveSQL());
			datasrc.setDefaultTestOnBorrow(true);
			/**
			 * wait while connection is initialized
			 */
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
	@Override
	public Connection getConnection() throws SQLException, ClassNotFoundException, DBNotDefinedException {		
		if(DBType.DB_NOT_DEFINED == this.getType()) 
			throw new DBNotDefinedException("Database "+getName()+" hasn't been defined in DBforBix local file config yet!");
		if (datasrc == null ) createConnection();
		return datasrc.getConnection();
	}
	
	@Override
	public void reconnect(){
		LOG.warn("Trying to reconnect...");
		abort();
		try {
			createConnection();
			LOG.warn("Reconnected.");
		} catch (ClassNotFoundException | SQLException e) {
			LOG.warn("Reconnection has failed.");
			e.printStackTrace();
			try {
				LOG.warn("Sleeping 60 seconds...");
				Thread.sleep(60000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Override
	public void abort(){
		try {
			if(null!=datasrc) datasrc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		datasrc=null;
	}
		
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String getURL() {
		return url;
	}
	
	@Override
	public String getUser() {
		return user;
	}
	
	@Override
	public String getPassword() {
		return passwd;
	}
	
	@Override
	public Integer getMaxActive() {
		return maxactive;
	}
	public Integer getMaxIdle() {
		return maxidle;
	}
	@Override
	public Integer getMaxWaitMillis() {
		return maxwaitmillis;
	}
	public boolean getPersistence() {
		return persistence;
	}

	@Override
	public Integer getQueryTimeout() {
		return queryTimeout;
	}
	
	@Override
	public String[] getDiscoveryItems() {
		return new String[0];
	}

	@Override
	public Object getDiscovery(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasServerItems() {
		return false;
	}

	@Override
	public boolean hasUserItems() {
		return false;
	}
	
	public boolean hasPersistence() {
		return this.persistence;
	}
	
	@Override
	public boolean hasDatabaseItems() {
		return false;
	}

	@Override
	public boolean hasSchemaItems() {
		return false;
	}

	@Override
	public boolean hasTablespaceItems() {
		return false;
	}
}
